package com.ruoyi.project.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.ruoyi.framework.util.RedisUtil;
import com.ruoyi.project.domain.SysOrderItem;
import com.ruoyi.project.domain.SysProduct;
import com.ruoyi.project.mapper.SysOrderItemMapper;
import com.ruoyi.project.mapper.SysProductMapper;
import com.ruoyi.project.mapper.SysShoppingCarMapper;
import com.ruoyi.project.service.ISysCounterService;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.utils.Constant;
import com.ruoyi.system.utils.Result;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.SysOrderMapper;
import com.ruoyi.project.domain.SysOrder;
import com.ruoyi.project.service.ISysOrderService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 订单Service业务层处理
 *
 * @author zhenxing.dong
 * @date 2020-05-06
 */
@Service
public class SysOrderServiceImpl implements ISysOrderService {
    @Autowired
    private SysOrderMapper sysOrderMapper;

    @Autowired
    private SysProductMapper sysProductMapper;

    @Autowired
    private SysOrderItemMapper sysOrderItemMapper;

    @Autowired
    private SysShoppingCarMapper sysShoppingCarMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(SysOrderServiceImpl.class);

    @Autowired
    private ISysCounterService counterService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private KafkaTemplate kafkaTemplate;
    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    @Override
    public ModelAndView getUserOrder() {
        LOGGER.info("===============查看订单==============");
        ModelAndView modelAndView = new ModelAndView("project/order/order");
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        // 用户所有订单
        SysOrder userOrder = new SysOrder();
        userOrder.setUserId(user.getUserId());
        List<SysOrder> allOrderList = sysOrderMapper.selectSysOrderList(userOrder);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        // 用户已支付订单
        userOrder.setStatus(1);
        List<SysOrder> paidOrderList = sysOrderMapper.selectSysOrderList(userOrder);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        // 用户未支付订单
        userOrder.setStatus(0);
        List<SysOrder> unpaidOrderList = sysOrderMapper.selectSysOrderList(userOrder);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        modelAndView.addObject("allOrderList", allOrderList);
        modelAndView.addObject("paidOrderList", paidOrderList);
        modelAndView.addObject("unpaidOrderList", unpaidOrderList);
        return modelAndView;
    }

    @Override
    public ModelAndView userOrderDetail(String orderId) {
        LOGGER.info("===============用户查看订单详情==============");
        ModelAndView modelAndView = new ModelAndView("project/order/orderDetail");
        SysOrder order = sysOrderMapper.selectSysOrderById(orderId);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        LOGGER.info("订单：" + JSON.toJSONString(order));
        SysOrderItem item = new SysOrderItem();
        item.setOrderId(order.getOrderCode());
        List<SysOrderItem> orderItemList = sysOrderItemMapper.selectSysOrderItemList(item);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        Map<SysProduct, SysOrderItem> map = new HashMap<>(Constant.ORDERITEM_MAP_CAPACITY);
        for (SysOrderItem orderItem : orderItemList) {
            SysProduct product = sysProductMapper.selectSysProductById(orderItem.getProductId());
            counterService.updateCounter(Constant.DISK_READ_COUNTER);
            map.put(product, orderItem);
        }
        modelAndView.addObject("order", order);
        modelAndView.addObject("map", map);
        return modelAndView;
    }

    /**
     * 查询订单
     *
     * @param id 订单ID
     * @return 订单
     */
    @Override
    public SysOrder selectSysOrderById(String id) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysOrderMapper.selectSysOrderById(id);
    }

    /**
     * 查询订单列表
     *
     * @param sysOrder 订单
     * @return 订单
     */
    @Override
    public List<SysOrder> selectSysOrderList(SysOrder sysOrder) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysOrderMapper.selectSysOrderList(sysOrder);
    }

    /**
     * 方法说明：购物车添加订单
     *
     * @param numArr   商品数量数组
     * @param idArr    商品id数组
     * @param priceArr 商品单价数组
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addByCar(String[] numArr, String[] idArr, String[] priceArr) {
        LOGGER.info("===============生成订单==============");
        Result result = new Result();
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        // 下单时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String createTime = dateFormat.format(new Date());
        // 订单号
        String orderCode = UUID.randomUUID().toString();

        //redis中用户购物车标记key
        String userCartFlag = "cart:" + String.valueOf(user.getUserId());
        String productFlag = "product:";

        LOGGER.info("下单时间：" + createTime);
        LOGGER.info("订单号：" + orderCode);
        // 总金额
        double totalPrice = 0.0;
        for (int i = 0; i < numArr.length && i < priceArr.length; i++) {
            totalPrice += Integer.parseInt(numArr[i]) * Double.parseDouble(priceArr[i]);
        }
        // 生成订单
        SysOrder order = new SysOrder();
        order.setId(UUID.randomUUID().toString());
        order.setCreateTime(createTime);
        order.setOrderCode(orderCode);
        order.setTotalPrice(totalPrice);
        order.setStatus(0);
        order.setUserId(user.getUserId());
        // 发送订单对象至队列
        kafkaTemplate.send("insertOrder", JSON.toJSONString(order));
//        int orderFlag = sysOrderMapper.insertSysOrder(order);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        int[] orderItemFlags = new int[idArr.length];
        for (int j = 0; j < idArr.length; j++) {
            String productId = idArr[j];
            int number = Integer.parseInt(numArr[j]);
            // 添加订单项
            SysOrderItem orderItem = new SysOrderItem();
            orderItem.setId(UUID.randomUUID().toString());
            orderItem.setProductNum(number);
            orderItem.setProductId(productId);
            orderItem.setOrderId(order.getOrderCode());

            try {
                ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send("insertOrderItem", JSON.toJSONString(orderItem));
                //这里我们可以获取到生产者消息是否提交成功
                SendResult<Integer, String> integerStringSendResult = future.get();
                RecordMetadata recordMetadata = integerStringSendResult.getRecordMetadata();
                if (null != recordMetadata) {
                    orderItemFlags[j] = 1;
                }
            } catch (InterruptedException e) {
                LOGGER.error("{}", e);
            } catch (ExecutionException e) {
                LOGGER.error("{}", e);
            }
//            orderItemFlags[j] = sysOrderItemMapper.insertSysOrderItem(orderItem);
            counterService.updateCounter(Constant.DISK_READ_COUNTER);
            // 更新商品库存
            SysProduct product = sysProductMapper.selectSysProductById(productId);
            counterService.updateCounter(Constant.DISK_READ_COUNTER);
            product.setProductCount(product.getProductCount() - number);
            sysProductMapper.updateSysProduct(product);
            //对通过购物车购买的商品的次数+1
            redisUtil.hincrBy(Constant.ADD_BY_CAR_KEY, productFlag + productId, 1);
        }
        // 清空用户购物车
        sysShoppingCarMapper.deleteSysShoppingCarByUserId(user.getUserId());
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        //情况购物车缓存
        redisUtil.del(userCartFlag);

        // 判断所有操作是否成功(标志是否都为1)
        int orderItemFlag = 1;
        for (int flag : orderItemFlags) {
            orderItemFlag *= flag;
        }
        // 插入结果
        if (orderItemFlag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
            //更新计数器
            counterService.updateCounter(Constant.ORDER_COUNT_BY_TIME);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 新增订单
     *
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertSysOrder(String id, Integer number) {
        LOGGER.info("===============立即购买==============");
        LOGGER.info("商品id：" + id + " 购买数量：" + number);
        Result result = new Result();
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        if (null == user) {
            result.setMsg(Constant.NOUSER_MSG);
            return result;
        }
        //检查商品价格及库存
        SysProduct product = sysProductMapper.selectSysProductById(id);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        if (product.getProductCount() < number) {
            result.setMsg("unEnough");
            return result;
        }
        double price = product.getProductPrice();
        // 总金额
        double totalPrice = price * number;
        // 下单时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String createTime = dateFormat.format(new Date());

        // 订单号
        String orderCode = UUID.randomUUID().toString();

        // 生成订单
        SysOrder order = new SysOrder();
        order.setId(UUID.randomUUID().toString());
        order.setOrderCode(orderCode);
        order.setCreateTime(createTime);
        order.setStatus(0);
        order.setUserId(user.getUserId());
        order.setTotalPrice(totalPrice);

        //先插入数据库，后期加入队列
        int orderFlag = sysOrderMapper.insertSysOrder(order);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        // 生成订单项
        SysOrderItem orderItem = new SysOrderItem();
        orderItem.setId(UUID.randomUUID().toString());
        orderItem.setOrderId(order.getOrderCode());
        orderItem.setProductId(id);
        orderItem.setProductNum(number);
        int orderItemFlag = sysOrderItemMapper.insertSysOrderItem(orderItem);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        // 更新商品库存
        product.setProductCount(product.getProductCount() - number);
        int productFlag = sysProductMapper.updateSysProduct(product);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        // 判断所有操作是否成功(标志是否都为1)
        if (orderFlag == 1 && productFlag == 1 && orderItemFlag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
            //更新订单量
            redisUtil.incrBy(Constant.ORDER_COUNT, Integer.toUnsignedLong(1));

            //更新计数器
            counterService.updateCounter(Constant.ORDER_COUNT_BY_TIME);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：用户支付订单
     * 支付订单时更新订单状态为已支付，
     * 并根据积分规则计算本次订单用户所能获得的积分，
     * 然后更新用户的积分
     *
     * @param id 订单id
     * @return Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result payOrder(String id) {
        LOGGER.info("===============用户支付订单==============");
        LOGGER.info("订单号：" + id);
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        Result result = new Result();
        // 更新订单状态为已支付
        SysOrder order = sysOrderMapper.selectSysOrderById(id);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        order.setStatus(1);
        int orderFlag = sysOrderMapper.updateSysOrder(order);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        double totalPrice = order.getTotalPrice();
        LOGGER.info("总金额：" + totalPrice);

        // 操作是否成功
        if (orderFlag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
            result.setData(10);

            //redis中记录成交额
            redisUtil.incrByFloat(Constant.TURNOVER, totalPrice);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 修改订单
     *
     * @param sysOrder 订单
     * @return 结果
     */
    @Override
    public int updateSysOrder(SysOrder sysOrder) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysOrderMapper.updateSysOrder(sysOrder);
    }

    /**
     * 删除订单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderByIds(String ids) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysOrderMapper.deleteSysOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单信息
     *
     * @param id 订单ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderById(String id) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysOrderMapper.deleteSysOrderById(id);
    }
}
