package com.ruoyi.project.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.SysShoppingCarMapper;
import com.ruoyi.project.domain.SysShoppingCar;
import com.ruoyi.project.service.ISysShoppingCarService;
import com.ruoyi.common.core.text.Convert;

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
    public int insertSysShoppingCar(SysShoppingCar sysShoppingCar)
    {
        return sysShoppingCarMapper.insertSysShoppingCar(sysShoppingCar);
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
