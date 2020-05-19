package com.ruoyi.project.controller;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.project.service.ISysLogAnalyseService;
import com.ruoyi.project.service.impl.SysCounterServiceImpl;
import com.ruoyi.system.utils.Constant;
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
import com.ruoyi.project.domain.SysSystemCount;
import com.ruoyi.project.service.ISysSystemCountService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * counterController
 * 
 * @author zhenxing.dong
 * @date 2020-05-18
 */
@Controller
@RequestMapping("/counter")
public class SysSystemCountController extends BaseController
{
    private String prefix = "project/counter";

    @Autowired
    private ISysLogAnalyseService logAnalyseService;

    @Autowired
    private ISysSystemCountService sysSystemCountService;

    @RequiresPermissions("project:counter:view")
    @GetMapping()
    public String counter(ModelMap modelMap)
    {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG,"admin access the counter page",Constant.INFO,Constant.LOG_TIMEOUT);
        List<Integer> precisionList = new ArrayList<>();
        for (int i : SysCounterServiceImpl.PRECISION){
            precisionList.add(i);
        }
        modelMap.put("precisionList",precisionList);

        return prefix + "/counter";
    }

    /**
     * 查询counter列表
     */
    @RequiresPermissions("project:counter:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(String precision)
    {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG,"get counter data",Constant.INFO,Constant.LOG_TIMEOUT);
        startPage();
        List<SysSystemCount> list = sysSystemCountService.selectSysSystemCountList(Integer.parseInt(precision));
        return getDataTable(list);
    }
}
