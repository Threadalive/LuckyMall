package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.project.domain.SysShoppingCar;

/**
 * 购物车Mapper接口
 * 
 * @author zhenxing.dong
 * @date 2020-05-08
 */
public interface SysShoppingCarMapper 
{
    /**
     * 查询购物车
     * 
     * @param id 购物车ID
     * @return 购物车
     */
    public SysShoppingCar selectSysShoppingCarById(String id);

    /**
     * 根据商品id以及用户id查找商品
     * @param sysShoppingCar
     * @return
     */
    public SysShoppingCar selectSysShoppingCarByIdAndProductId(SysShoppingCar sysShoppingCar);

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
    public int insertSysShoppingCar(SysShoppingCar sysShoppingCar);

    /**
     * 修改购物车
     * 
     * @param sysShoppingCar 购物车
     * @return 结果
     */
    public int updateSysShoppingCar(SysShoppingCar sysShoppingCar);

    /**
     * 删除购物车
     * 
     * @param id 购物车ID
     * @return 结果
     */
    public int deleteSysShoppingCarById(String id);

    /**
     * 批量删除购物车
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysShoppingCarByIds(String[] ids);
}
