package com.ruoyi.project.service;

import java.util.List;
import com.ruoyi.project.domain.SysOrder;
import org.springframework.web.servlet.ModelAndView;

/**
 * 订单Service接口
 * 
 * @author zhenxing.dong
 * @date 2020-05-06
 */
public interface ISysOrderService 
{
    public ModelAndView getUserOrder();

    public ModelAndView userOrderDetail(Long orderId);
    /**
     * 查询订单
     * 
     * @param id 订单ID
     * @return 订单
     */
    public SysOrder selectSysOrderById(Long id);

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
     * 批量删除订单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysOrderByIds(String ids);

    /**
     * 删除订单信息
     * 
     * @param id 订单ID
     * @return 结果
     */
    public int deleteSysOrderById(Long id);
}
