package com.ruoyi.project.controller;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ruoyi.project.common.Constant;
import com.ruoyi.project.common.Result;
import com.ruoyi.project.domain.SysProductType;
import com.ruoyi.project.service.ISysProductTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2020-05-04
 */
@Controller
@RequestMapping("/productType")
public class SysProductTypeController extends BaseController
{
    private String prefix = "system/type";

    private static final Logger LOGGER = LoggerFactory.getLogger(SysProductTypeController.class);
    @Autowired
    private ISysProductTypeService sysProductTypeService;

    @RequiresPermissions("system:type:view")
    @GetMapping()
    public String type()
    {
        return prefix + "/type";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:type:list")
    @PostMapping("/listType")
    @ResponseBody
    public Result<List<SysProductType>> list()
    {
        Result<List<SysProductType>> result = new Result<>();
        SysProductType sysProductType = new SysProductType();
        List<SysProductType> list = sysProductTypeService.selectSysProductTypeList(sysProductType);
        if (list == null) {
            result.setMsg(Constant.ERROR_MSG);
        } else {
            result.setMsg(Constant.SUCCESS_MSG);
            result.setData(list);
        }
        LOGGER.info("商品种类列表：" + JSON.toJSONString(result));
        return result;
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:type:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProductType sysProductType)
    {
        List<SysProductType> list = sysProductTypeService.selectSysProductTypeList(sysProductType);
        ExcelUtil<SysProductType> util = new ExcelUtil<SysProductType>(SysProductType.class);
        return util.exportExcel(list, "type");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("system:type:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProductType sysProductType)
    {
        return toAjax(sysProductTypeService.insertSysProductType(sysProductType));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysProductType sysProductType = sysProductTypeService.selectSysProductTypeById(id);
        mmap.put("sysProductType", sysProductType);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:type:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProductType sysProductType)
    {
        return toAjax(sysProductTypeService.updateSysProductType(sysProductType));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("system:type:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysProductTypeService.deleteSysProductTypeByIds(ids));
    }
}
