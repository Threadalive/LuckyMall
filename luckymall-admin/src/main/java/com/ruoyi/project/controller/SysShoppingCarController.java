package com.ruoyi.project.controller;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.domain.SysProduct;
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
import com.ruoyi.project.domain.SysShoppingCar;
import com.ruoyi.project.service.ISysShoppingCarService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.servlet.ModelAndView;

/**
 * 购物车Controller
 * 
 * @author zhenxing.dong
 * @date 2020-05-08
 */
@Controller
@RequestMapping("/shoppingCar")
public class SysShoppingCarController extends BaseController
{
    private String prefix = "project/shoppingCar";

    @Autowired
    private ISysShoppingCarService sysShoppingCarService;

    @Autowired
    private ISysLogAnalyseService logAnalyseService;

    /**
     * 方法说明：用户查看购物车
     *
     * @return org.springframework.web.servlet.ModelAndView 购物车视图
     */
    @GetMapping()
    public String userCart(ModelMap modelMap) {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG,"user access the cart's page",Constant.INFO,Constant.LOG_TIMEOUT);
        Map<SysProduct,SysShoppingCar> map = sysShoppingCarService.userCar();
        modelMap.put("cartMap",map);
        return prefix + "/cart";
    }

    /**
     * 查询购物车列表
     */
    @RequiresPermissions("project:shoppingCar:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysShoppingCar sysShoppingCar)
    {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG,"list cart",Constant.INFO,Constant.LOG_TIMEOUT);
        startPage();
        List<SysShoppingCar> list = sysShoppingCarService.selectSysShoppingCarList(sysShoppingCar);
        return getDataTable(list);
    }

    /**
     * 导出购物车列表
     */
    @RequiresPermissions("project:shoppingCar:export")
    @Log(title = "购物车", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysShoppingCar sysShoppingCar)
    {
        List<SysShoppingCar> list = sysShoppingCarService.selectSysShoppingCarList(sysShoppingCar);
        ExcelUtil<SysShoppingCar> util = new ExcelUtil<SysShoppingCar>(SysShoppingCar.class);
        return util.exportExcel(list, "shoppingCar");
    }

    /**
     * 新增购物车
     */
    @GetMapping("/add")
    public String add()
    {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG,"get add cart page",Constant.INFO,Constant.LOG_TIMEOUT);
        return prefix + "/add";
    }

    /**
     * 新增保存购物车
     */
    @RequiresPermissions("project:shoppingCar:add")
    @Log(title = "购物车", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Result addSave(SysShoppingCar sysShoppingCar)
    {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG,"add cart",Constant.INFO,Constant.LOG_TIMEOUT);
        return sysShoppingCarService.insertSysShoppingCar(sysShoppingCar);
    }

    /**
     * 修改购物车
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        SysShoppingCar sysShoppingCar = sysShoppingCarService.selectSysShoppingCarById(id);
        mmap.put("sysShoppingCar", sysShoppingCar);
        return prefix + "/edit";
    }

    /**
     * 修改保存购物车
     */
    @RequiresPermissions("project:shoppingCar:edit")
    @Log(title = "购物车", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysShoppingCar sysShoppingCar)
    {
        return toAjax(sysShoppingCarService.updateSysShoppingCar(sysShoppingCar));
    }

    /**
     * 删除购物车
     */
    @RequiresPermissions("project:shoppingCar:remove")
    @Log(title = "购物车", businessType = BusinessType.DELETE)
    @GetMapping( "/remove")
    public String remove(String cartId)
    {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG,"remove cart",Constant.INFO,Constant.LOG_TIMEOUT);
        if (1 == sysShoppingCarService.deleteSysShoppingCarById(cartId)){
           return  "redirect:/shoppingCar";
        }else {
            return "error/business";
        }
    }
}
