package com.ruoyi.project.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.domain.SysProduct;
import com.ruoyi.project.domain.SysShoppingCar;
import com.ruoyi.system.utils.Result;
import org.springframework.web.servlet.ModelAndView;

/**
 * 购物车Service接口
 * 
 * @author zhenxing.dong
 * @date 2020-05-08
 */
public interface ISysShoppingCarService 
{

    public Map<SysProduct,SysShoppingCar> userCar();
    /**
     * 查询购物车
     * 
     * @param id 购物车ID
     * @return 购物车
     */
    public SysShoppingCar selectSysShoppingCarById(String id);

    /**
     * 查询购物车列表
     * 
     * @param sysShoppingCar 购物车
     * @return 购物车集合
     */
    public List<SysShoppingCar> selectSysShoppingCarList(SysShoppingCar sysShoppingCar);

    /**
     * 新增购物车
     * 
     * @param sysShoppingCar 购物车
     * @return 结果
     */
    public Result insertSysShoppingCar(SysShoppingCar sysShoppingCar);

    /**
     * 修改购物车
     * 
     * @param sysShoppingCar 购物车
     * @return 结果
     */
    public int updateSysShoppingCar(SysShoppingCar sysShoppingCar);

    /**
     * 批量删除购物车
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysShoppingCarByIds(String ids);

    /**
     * 删除购物车信息
     * 
     * @param id 购物车ID
     * @return 结果
     */
    public int deleteSysShoppingCarById(String id);
}
