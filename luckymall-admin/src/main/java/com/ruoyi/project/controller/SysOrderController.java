package com.ruoyi.project.controller;

import java.util.List;

import com.ruoyi.project.service.ISysLogAnalyseService;
import com.ruoyi.system.utils.Constant;
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
public class SysOrderController extends BaseController {
    private String prefix = "project/order";

    @Autowired
    private ISysOrderService sysOrderService;

    @Autowired
    private ISysLogAnalyseService logAnalyseService;

    @RequiresPermissions("project:order:view")
    @GetMapping()
    public ModelAndView order() {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "user access the order page", Constant.INFO, Constant.LOG_TIMEOUT);
        ModelAndView orderModel = sysOrderService.getUserOrder();
        return orderModel;
    }

    @GetMapping("orderAdmin")
    public String orderAdmin() {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "admin access the order admin page", Constant.INFO, Constant.LOG_TIMEOUT);
        return prefix + "/admin_order";
    }

    /**
     * 方法说明：用户支付订单
     *
     * @param id 订单id
     * @return com.luckymall.common.Result 结果
     */
    @PostMapping("/pay")
    @ResponseBody
    public Result payOrder(String id) {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "user pay for the order", Constant.INFO, Constant.LOG_TIMEOUT);
        Result result = sysOrderService.payOrder(id);
        return result;
    }

    @PostMapping("addByCar")
    @ResponseBody
    public Result addByCar(String[] numArr, String[] idArr, String[] priceArr) {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "user add order by car", Constant.INFO, Constant.LOG_TIMEOUT);
        Result result = sysOrderService.addByCar(numArr, idArr, priceArr);
        return result;
    }

    /**
     * 查询订单列表
     */
    @RequiresPermissions("project:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysOrder sysOrder) {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "select order list", Constant.INFO, Constant.LOG_TIMEOUT);
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
    public AjaxResult export(SysOrder sysOrder) {
        List<SysOrder> list = sysOrderService.selectSysOrderList(sysOrder);
        ExcelUtil<SysOrder> util = new ExcelUtil<SysOrder>(SysOrder.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 新增订单
     */
    @GetMapping("/add")
    public String add() {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "get add order page", Constant.INFO, Constant.LOG_TIMEOUT);
        return prefix + "/add";
    }

    /**
     * 新增保存订单
     */
    @RequiresPermissions("project:order:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Result addSave(String id, Integer number) {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "add and save an order", Constant.INFO, Constant.LOG_TIMEOUT);
        return sysOrderService.insertSysOrder(id, number);
    }

    /**
     * 修改订单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "get edit order msg page", Constant.INFO, Constant.LOG_TIMEOUT);
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
    public AjaxResult editSave(SysOrder sysOrder) {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "edit order msg", Constant.INFO, Constant.LOG_TIMEOUT);
        return toAjax(sysOrderService.updateSysOrder(sysOrder));
    }

    /**
     * 删除订单
     */
    @RequiresPermissions("project:order:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "delete an order", Constant.INFO, Constant.LOG_TIMEOUT);
        return toAjax(sysOrderService.deleteSysOrderByIds(ids));
    }
}
