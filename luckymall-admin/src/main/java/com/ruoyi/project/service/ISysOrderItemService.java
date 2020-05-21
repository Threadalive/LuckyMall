package com.ruoyi.project.service;

import java.util.List;

import com.ruoyi.project.domain.SysOrderItem;

/**
 * 订单项Service接口
 *
 * @author zhenxing.dong
 * @date 2020-05-14
 */
public interface ISysOrderItemService {
    /**
     * 查询订单项
     *
     * @param id 订单项ID
     * @return 订单项
     */
    public SysOrderItem selectSysOrderItemById(String id);

    /**
     * 查询订单项列表
     *
     * @param sysOrderItem 订单项
     * @return 订单项集合
     */
    public List<SysOrderItem> selectSysOrderItemList(SysOrderItem sysOrderItem);

    /**
     * 新增订单项
     *
     * @param sysOrderItem 订单项
     * @return 结果
     */
    public int insertSysOrderItem(SysOrderItem sysOrderItem);

    /**
     * 修改订单项
     *
     * @param sysOrderItem 订单项
     * @return 结果
     */
    public int updateSysOrderItem(SysOrderItem sysOrderItem);

    /**
     * 批量删除订单项
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysOrderItemByIds(String ids);

    /**
     * 删除订单项信息
     *
     * @param id 订单项ID
     * @return 结果
     */
    public int deleteSysOrderItemById(String id);
}
