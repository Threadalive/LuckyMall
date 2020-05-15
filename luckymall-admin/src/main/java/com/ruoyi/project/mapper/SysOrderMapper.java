package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.project.domain.SysOrder;

/**
 * 订单Mapper接口
 * 
 * @author zhenxing.dong
 * @date 2020-05-06
 */
public interface SysOrderMapper 
{
    /**
     * 查询订单
     * 
     * @param id 订单ID
     * @return 订单
     */
    public SysOrder selectSysOrderById(String id);

    /**
     * 查询订单列表
     * 
     * @param sysOrder 订单
     * @return 订单集合
     */
    public List<SysOrder> selectSysOrderList(SysOrder sysOrder);

    /**
     * 新增订单
     * 
     * @param sysOrder 订单
     * @return 结果
     */
    public int insertSysOrder(SysOrder sysOrder);

    /**
     * 修改订单
     * 
     * @param sysOrder 订单
     * @return 结果
     */
    public int updateSysOrder(SysOrder sysOrder);

    /**
     * 删除订单
     * 
     * @param id 订单ID
     * @return 结果
     */
    public int deleteSysOrderById(String id);

    /**
     * 批量删除订单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysOrderByIds(String[] ids);
}
