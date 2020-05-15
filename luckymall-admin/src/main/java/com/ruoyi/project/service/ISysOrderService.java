package com.ruoyi.project.service;

import java.util.List;
import com.ruoyi.project.domain.SysOrder;
import com.ruoyi.system.utils.Result;
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

    public ModelAndView userOrderDetail(String orderId);
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
     * @return 结果
     */
    public Result insertSysOrder(String id,Integer number);

    public Result addByCar(String[] numArr,String[] idArr,String[] priceArr);

    public Result payOrder(String id);
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
    public int deleteSysOrderById(String id);
}
