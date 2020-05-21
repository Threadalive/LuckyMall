package com.ruoyi.project.service.impl;

import java.util.List;

import com.ruoyi.project.service.ISysCounterService;
import com.ruoyi.system.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.SysOrderItemMapper;
import com.ruoyi.project.domain.SysOrderItem;
import com.ruoyi.project.service.ISysOrderItemService;
import com.ruoyi.common.core.text.Convert;

/**
 * 订单项Service业务层处理
 *
 * @author zhenxing.dong
 * @date 2020-05-14
 */
@Service
public class SysOrderItemServiceImpl implements ISysOrderItemService {
    @Autowired
    private SysOrderItemMapper sysOrderItemMapper;

    @Autowired
    private ISysCounterService counterService;

    /**
     * 查询订单项
     *
     * @param id 订单项ID
     * @return 订单项
     */
    @Override
    public SysOrderItem selectSysOrderItemById(String id) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysOrderItemMapper.selectSysOrderItemById(id);
    }

    /**
     * 查询订单项列表
     *
     * @param sysOrderItem 订单项
     * @return 订单项
     */
    @Override
    public List<SysOrderItem> selectSysOrderItemList(SysOrderItem sysOrderItem) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysOrderItemMapper.selectSysOrderItemList(sysOrderItem);
    }

    /**
     * 新增订单项
     *
     * @param sysOrderItem 订单项
     * @return 结果
     */
    @Override
    public int insertSysOrderItem(SysOrderItem sysOrderItem) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysOrderItemMapper.insertSysOrderItem(sysOrderItem);
    }

    /**
     * 修改订单项
     *
     * @param sysOrderItem 订单项
     * @return 结果
     */
    @Override
    public int updateSysOrderItem(SysOrderItem sysOrderItem) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysOrderItemMapper.updateSysOrderItem(sysOrderItem);
    }

    /**
     * 删除订单项对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderItemByIds(String ids) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysOrderItemMapper.deleteSysOrderItemByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单项信息
     *
     * @param id 订单项ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderItemById(String id) {
        counterService.updateCounter(Constant.DISK_READ_COUNTER);
        return sysOrderItemMapper.deleteSysOrderItemById(id);
    }
}
