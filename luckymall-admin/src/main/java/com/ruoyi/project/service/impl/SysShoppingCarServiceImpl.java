package com.ruoyi.project.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.ruoyi.project.domain.SysProduct;
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
public class SysShoppingCarServiceImpl implements ISysShoppingCarService
{
    @Autowired
    private SysShoppingCarMapper sysShoppingCarMapper;

    @Autowired
    private ISysProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SysShoppingCarServiceImpl.class);
    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    @Override
    public ModelAndView userCar() {
        LOGGER.info("===============查看购物车==============");
        ModelAndView modelAndView = new ModelAndView("project/shoppingcar/cart");
        HttpSession session=request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        // Map<商品，该商品的购物车>
        Map<SysProduct,SysShoppingCar> productCartMap = new HashMap<>(Constant.CART_MAP_CAPACITY);
        // List<用户的购物车>
        SysShoppingCar car = new SysShoppingCar();
        car.setUserId(user.getUserId());
        List<SysShoppingCar> cartList = this.selectSysShoppingCarList(car);
        for(SysShoppingCar cart:cartList){
            SysProduct product = productService.selectSysProductById(cart.getProductId());
            productCartMap.put(product,cart);
        }
        LOGGER.info("购物车map：" + JSON.toJSONString(productCartMap));
        modelAndView.addObject("cartMap",productCartMap);
        return modelAndView;
    }

    /**
     * 查询购物车
     *
     * @param id 购物车ID
     * @return 购物车
     */
    @Override
    public SysShoppingCar selectSysShoppingCarById(String id)
    {
        return sysShoppingCarMapper.selectSysShoppingCarById(id);
    }

    /**
     * 查询购物车列表
     *
     * @param sysShoppingCar 购物车
     * @return 购物车
     */
    @Override
    public List<SysShoppingCar> selectSysShoppingCarList(SysShoppingCar sysShoppingCar)
    {
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
    public Result insertSysShoppingCar(SysShoppingCar sysShoppingCar)
    {
        LOGGER.info("===============添加商品到购物车==============");
        LOGGER.info("商品id："+sysShoppingCar.getProductId()+" 数量："+sysShoppingCar.getNumber());
        Result result=new Result();
        result.setMsg(Constant.ERROR_MSG);
        HttpSession session=request.getSession();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String addTime = dateFormat.format(new Date());
        SysUser user = (SysUser) session.getAttribute("user");
        if(user==null){
            result.setMsg(Constant.NOUSER_MSG);
            return result;
        }
        // 先查看用户该商品的购物车是否已存在
        SysShoppingCar car = new SysShoppingCar();
        car.setUserId(user.getUserId());
        car.setProductId(sysShoppingCar.getProductId());
        SysShoppingCar cart = sysShoppingCarMapper.selectSysShoppingCarByIdAndProductId(car);

        // 若购物车不存在，则添加一个新的购物车;否则，修改已存在的购物车
        if(cart==null){
            sysShoppingCar.setId(UUID.randomUUID().toString());
            sysShoppingCar.setAddTime(addTime);
            sysShoppingCar.setUserId(user.getUserId());
            sysShoppingCarMapper.insertSysShoppingCar(sysShoppingCar);
            result.setMsg(Constant.SUCCESS_MSG);
        }else{
            cart.setAddTime(addTime);
            cart.setNumber(cart.getNumber()+sysShoppingCar.getNumber());
            sysShoppingCarMapper.updateSysShoppingCar(cart);
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
    public int updateSysShoppingCar(SysShoppingCar sysShoppingCar)
    {
        return sysShoppingCarMapper.updateSysShoppingCar(sysShoppingCar);
    }

    /**
     * 删除购物车对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysShoppingCarByIds(String ids)
    {
        return sysShoppingCarMapper.deleteSysShoppingCarByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除购物车信息
     *
     * @param id 购物车ID
     * @return 结果
     */
    @Override
    public int deleteSysShoppingCarById(String id)
    {
        return sysShoppingCarMapper.deleteSysShoppingCarById(id);
    }
}
