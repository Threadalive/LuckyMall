package com.ruoyi.project.controller;

import java.util.List;

import com.ruoyi.project.domain.SysProductType;
import com.ruoyi.project.service.ISysProductTypeService;
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
import com.ruoyi.project.domain.SysProduct;
import com.ruoyi.project.service.ISysProductService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 【请填写功能名称】Controller
 * 
 * @author zhenxing.dong
 * @date 2020-04-16
 */
@Controller
@RequestMapping("/product")
public class SysProductController extends BaseController
{
    private String prefix = "project/product";

    @Autowired
    private ISysProductService sysProductService;

    @Autowired
    private ISysProductTypeService productTypeService;

    @RequiresPermissions("system:product:view")
    @GetMapping("/productAdmin")
    public String product(ModelMap modelMap)
    {
        SysProductType productType = new SysProductType();

        List<SysProductType> productTypeList = productTypeService.selectSysProductTypeList(productType);
        modelMap.put("productTypeList",productTypeList);
        return prefix + "/product";
    }

    /**
     * 方法说明：方法说明：根据商品种类查找相关商品
     *
     * @param id   商品种类id
     * @param name 商品种类名
     * @return org.springframework.web.servlet.ModelAndView 返回商品视图
     */
    @RequestMapping("/findProductByType")
    public ModelAndView findProductByType(int id, String name) {
        ModelAndView modelAndView = sysProductService.findProductByType(id, name);
        return modelAndView;
    }

    /**
     * 方法说明：跳转到商品详情页
     *
     * @param id 商品id
     * @return org.springframework.web.servlet.ModelAndView 返回视图
     */
    @RequestMapping("/detail")
    public ModelAndView productDetail(String id) {
        ModelAndView modelAndView = new ModelAndView(prefix+"/productDetail");
        SysProduct productDetail = sysProductService.selectSysProductById(id);
        modelAndView.addObject("product",productDetail);
        return modelAndView;
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:product:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProduct sysProduct)
    {
        startPage();
        List<SysProduct> list = sysProductService.selectSysProductList(sysProduct);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:product:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProduct sysProduct)
    {
        List<SysProduct> list = sysProductService.selectSysProductList(sysProduct);
        ExcelUtil<SysProduct> util = new ExcelUtil<SysProduct>(SysProduct.class);
        return util.exportExcel(list, "product");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap)
    {
        SysProductType productType = new SysProductType();

        List<SysProductType> productTypeList = productTypeService.selectSysProductTypeList(productType);
        modelMap.put("productTypeList",productTypeList);
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("system:product:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MultipartFile file, SysProduct sysProduct)
    {

        return toAjax(sysProductService.insertSysProduct(file,sysProduct));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        SysProduct sysProduct = sysProductService.selectSysProductById(id);
        mmap.put("sysProduct", sysProduct);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:product:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProduct sysProduct)
    {
        return toAjax(sysProductService.updateSysProduct(sysProduct));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("system:product:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysProductService.deleteSysProductByIds(ids));
    }
}
