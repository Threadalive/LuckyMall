package com.ruoyi.project.controller;

import com.ruoyi.project.service.ISysLogAnalyseService;
import com.ruoyi.system.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2020/4/14 14:30
 */
@Controller
@RequestMapping("/log")
public class SysLogAnalyseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogAnalyseController.class);

    @Autowired
    private ISysLogAnalyseService logAnalyseService;

    private final String PREFIX = "project/log";

    @GetMapping
    public String logMonitor(ModelMap modelMap) {
        Map<String,String> commonLogs = new HashMap<>(128);
        List<String> recentLogs = logAnalyseService.getRecentLogs(Constant.CURRENCY_LOG,Constant.INFO);
        Set<Tuple> commonLogsInTuple = logAnalyseService.getCommonLogs(Constant.CURRENCY_LOG,Constant.INFO);
        for (Tuple tuple : commonLogsInTuple){
            commonLogs.put(tuple.getElement(),String.valueOf(tuple.getScore()));
        }
        modelMap.put("recentLogs",recentLogs);
        modelMap.put("commonLogs",commonLogs);
        return PREFIX+"/logMonitor";
    }
}
