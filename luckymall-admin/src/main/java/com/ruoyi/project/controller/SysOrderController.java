package com.ruoyi.project.controller;

import java.util.List;

import com.ruoyi.system.utils.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.project.domain.SysOrder;
import com.ruoyi.project.service.ISysOrderService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.servlet.ModelAndView;

/**
 * 订单Controller
 * 
 * @author zhenxing.dong
 * @date 2020-05-06
 */
@Controller
@RequestMapping("/order")
public class SysOrderController extends BaseController
{
    private String prefix = "project/order";

    @Autowired
    private ISysOrderService sysOrderService;

    @RequiresPermissions("project:order:view")
    @GetMapping()
    public ModelAndView order()
    {
        ModelAndView orderModel = sysOrderService.getUserOrder();
        return orderModel;
    }

    @GetMapping("orderAdmin")
    public String orderAdmin(){
        return prefix + "/admin_order.html";
    }

    /**
     * 方法说明：用户支付订单
     * @param id 订单id
     * @return com.luckymall.common.Result 结果
     */
    @PostMapping("/pay")
    @ResponseBody
    public Result payOrder(String id){
        Result result = sysOrderService.payOrder(id);
        return result;
    }

    @PostMapping("addByCar")
    @ResponseBody
    public Result addByCar(String[] numArr,String[] idArr,String[] priceArr){
        Result result =sysOrderService.addByCar(numArr,idArr,priceArr);
        return result;
    }
    /**
     * 查询订单列表
     */
    @RequiresPermissions("project:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysOrder sysOrder)
    {
        startPage();
        List<SysOrder> list = sysOrderService.selectSysOrderList(sysOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @RequiresPermissions("project:order:export")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysOrder sysOrder)
    {
        List<SysOrder> list = sysOrderService.selectSysOrderList(sysOrder);
        ExcelUtil<SysOrder> util = new ExcelUtil<SysOrder>(SysOrder.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 新增订单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存订单
     */
    @RequiresPermissions("project:order:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Result addSave(String id,Integer number)
    {
        return sysOrderService.insertSysOrder(id,number);
    }

    /**
     * 修改订单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        SysOrder sysOrder = sysOrderService.selectSysOrderById(id);
        mmap.put("sysOrder", sysOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单
     */
    @RequiresPermissions("project:order:edit")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysOrder sysOrder)
    {
        return toAjax(sysOrderService.updateSysOrder(sysOrder));
    }

    /**
     * 删除订单
     */
    @RequiresPermissions("project:order:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysOrderService.deleteSysOrderByIds(ids));
    }
}
