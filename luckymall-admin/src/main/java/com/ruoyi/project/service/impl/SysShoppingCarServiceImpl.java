package com.ruoyi.project.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.ruoyi.framework.util.RedisUtil;
import com.ruoyi.project.domain.SysProduct;
import com.ruoyi.project.service.ISysCounterService;
import com.ruoyi.project.service.ISysProductService;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.utils.Constant;
import com.ruoyi.system.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.SysShoppingCarMapper;
import com.ruoyi.project.domain.SysShoppingCar;
import com.ruoyi.project.service.ISysShoppingCarService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 购物车Service业务层处理
 *
 * @author zhenxing.dong
 * @date 2020-05-08
 */
@Service
public class SysShoppingCarServiceImpl implements ISysShoppingCarService {
    @Autowired
    private SysShoppingCarMapper sysShoppingCarMapper;

    @Autowired
    private ISysProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SysShoppingCarServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;
    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysCounterService counterService;

    /**
     * 获取用户购物车，
     * 若redis中存在，
     * 则首先从缓存中取标志
     *
     * @return
     */
    @Override
    public Map<SysProduct, SysShoppingCar> userCar() {
        LOGGER.info("===============查看购物车==============");
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");

        String userCartFlag = "cart:" + String.valueOf(user.getUserId());

        // Map<商品，该商品的购物车>
        Map<SysProduct, SysShoppingCar> productCartMap = new HashMap<>(Constant.CART_MAP_CAPACITY);
        // List<用户的购物车>
        SysShoppingCar car = new SysShoppingCar();
        car.setUserId(user.getUserId());

        //若redis缓存中有该用户购物车缓存
        if (redisUtil.exists(userCartFlag)) {
            Set<String> productIds = redisUtil.hkeys(userCartFlag);
            for (String productId : productIds) {
                //通过缓存的标记获取商品和购物车
                SysProduct product = productService.selectSysProductById(productId);
                SysShoppingCar cart = sysShoppingCarMapper.selectSysShoppingCarById(redisUtil.hget(userCartFlag, productId));
                counterService.updateCounter(Constant.DISK_READ_COUNTER);
                productCartMap.put(product, cart);
            }
        } else {
            List<SysShoppingCar> cartList = this.selectSysShoppingCarList(car);
            for (SysShoppingCar cart : cartList) {
                SysProduct product = productService.selectSysProductById(cart.getProductId());
                counterService.updateCounter(Constant.DISK_READ_COUNTER);
                productCartMap.put(product, cart);
            }
            LOGGER.info("购物车map：" + JSON.toJSONString(productCartMap));
        }
        return productCartMap;
    }

    /**
     * 查询购物车
     *
     * @param id 购物车ID
     * @return 购物车
     */
    @Override
    public SysShoppingCar selectSysShoppingCarById(String id) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysShoppingCarMapper.selectSysShoppingCarById(id);
    }

    /**
     * 查询购物车列表
     *
     * @param sysShoppingCar 购物车
     * @return 购物车
     */
    @Override
    public List<SysShoppingCar> selectSysShoppingCarList(SysShoppingCar sysShoppingCar) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysShoppingCarMapper.selectSysShoppingCarList(sysShoppingCar);
    }

    /**
     * 新增购物车
     *
     * @param sysShoppingCar 购物车
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertSysShoppingCar(SysShoppingCar sysShoppingCar) {
        LOGGER.info("===============添加商品到购物车==============");
        LOGGER.info("商品id：" + sysShoppingCar.getProductId() + " 数量：" + sysShoppingCar.getNumber());
        Result result = new Result();
        result.setMsg(Constant.ERROR_MSG);
        HttpSession session = request.getSession();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String addTime = dateFormat.format(new Date());
        SysUser user = (SysUser) session.getAttribute("user");
        if (user == null) {
            result.setMsg(Constant.NOUSER_MSG);
            return result;
        }
        String userCartFlag = "cart:" + String.valueOf(user.getUserId());
        String product = "product:" + sysShoppingCar.getProductId();

        // 先查看用户该商品的购物车是否已存在
        SysShoppingCar car = new SysShoppingCar();
        car.setUserId(user.getUserId());
        car.setProductId(sysShoppingCar.getProductId());
        SysShoppingCar cart = sysShoppingCarMapper.selectSysShoppingCarByIdAndProductId(car);
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        // 若购物车不存在，则添加一个新的购物车;否则，修改已存在的购物车
        if (cart == null) {
            sysShoppingCar.setId(UUID.randomUUID().toString());
            sysShoppingCar.setAddTime(addTime);
            sysShoppingCar.setUserId(user.getUserId());
            sysShoppingCarMapper.insertSysShoppingCar(sysShoppingCar);

            //将商品id与购物车id的映射保存进redis中
            redisUtil.hset(userCartFlag, sysShoppingCar.getProductId(), sysShoppingCar.getId());

            //购物车缓存在一周后失效，节省内存
            redisUtil.expire(userCartFlag, Constant.ONE_WEEK_IN_SECONDS, 0);

            //更新该商品被添加进购物车次数
            redisUtil.hincrBy(product, "addCarNum", 1);

            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            cart.setAddTime(addTime);
            cart.setNumber(cart.getNumber() + sysShoppingCar.getNumber());
            sysShoppingCarMapper.updateSysShoppingCar(cart);
            counterService.updateCounter(Constant.DISK_READ_COUNTER);
            //将商品id与购物车id的映射保存进redis中
            redisUtil.hset(userCartFlag, sysShoppingCar.getProductId(), sysShoppingCar.getId());

            result.setMsg(Constant.SUCCESS_MSG);
        }
        return result;
    }

    /**
     * 修改购物车
     *
     * @param sysShoppingCar 购物车
     * @return 结果
     */
    @Override
    public int updateSysShoppingCar(SysShoppingCar sysShoppingCar) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysShoppingCarMapper.updateSysShoppingCar(sysShoppingCar);
    }

    /**
     * 删除购物车对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysShoppingCarByIds(String ids) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysShoppingCarMapper.deleteSysShoppingCarByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除购物车信息
     *
     * @param id 购物车ID
     * @return 结果
     */
    @Override
    public int deleteSysShoppingCarById(String id) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysShoppingCarMapper.deleteSysShoppingCarById(id);
    }
}
