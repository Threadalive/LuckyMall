package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.SysOrderMapper;
import com.ruoyi.project.domain.SysOrder;
import com.ruoyi.project.service.ISysOrderService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 订单Service业务层处理
 * 
 * @author zhenxing.dong
 * @date 2020-05-06
 */
@Service
public class SysOrderServiceImpl implements ISysOrderService 
{
    @Autowired
    private SysOrderMapper sysOrderMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(SysOrderServiceImpl.class);

    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    @Override
    public ModelAndView getUserOrder() {
        LOGGER.info("===============查看订单==============");
        ModelAndView modelAndView = new ModelAndView("project/order/order");
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        // 用户所有订单
        SysOrder userOrder = new SysOrder();
        userOrder.setId(user.getUserId());
        List<SysOrder> allOrderList = sysOrderMapper.selectSysOrderList(userOrder);
        // 用户已支付订单
        userOrder.setStatus(1);
        List<SysOrder> paidOrderList = sysOrderMapper.selectSysOrderList(userOrder);
        // 用户未支付订单
        userOrder.setStatus(0);
        List<SysOrder> unpaidOrderList = sysOrderMapper.selectSysOrderList(userOrder);

        modelAndView.addObject("allOrderList", allOrderList);
        modelAndView.addObject("paidOrderList", paidOrderList);
        modelAndView.addObject("unpaidOrderList", unpaidOrderList);
        return modelAndView;
    }

    /**
     * 查询订单
     * 
     * @param id 订单ID
     * @return 订单
     */
    @Override
    public SysOrder selectSysOrderById(Long id)
    {
        return sysOrderMapper.selectSysOrderById(id);
    }

    /**
     * 查询订单列表
     * 
     * @param sysOrder 订单
     * @return 订单
     */
    @Override
    public List<SysOrder> selectSysOrderList(SysOrder sysOrder)
    {
        return sysOrderMapper.selectSysOrderList(sysOrder);
    }

    /**
     * 新增订单
     * 
     * @param sysOrder 订单
     * @return 结果
     */
    @Override
    public int insertSysOrder(SysOrder sysOrder)
    {
        sysOrder.setCreateTime(DateUtils.getNowDate());
        return sysOrderMapper.insertSysOrder(sysOrder);
    }

    /**
     * 修改订单
     * 
     * @param sysOrder 订单
     * @return 结果
     */
    @Override
    public int updateSysOrder(SysOrder sysOrder)
    {
        return sysOrderMapper.updateSysOrder(sysOrder);
    }

    /**
     * 删除订单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderByIds(String ids)
    {
        return sysOrderMapper.deleteSysOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单信息
     * 
     * @param id 订单ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderById(Long id)
    {
        return sysOrderMapper.deleteSysOrderById(id);
    }
}
