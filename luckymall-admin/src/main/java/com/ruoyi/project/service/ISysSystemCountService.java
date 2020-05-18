package com.ruoyi.project.service;

import com.ruoyi.project.domain.SysSystemCount;

import java.util.List;

/**
 * counterService接口
 * 
 * @author zhenxing.dong
 * @date 2020-05-18
 */
public interface ISysSystemCountService 
{
    public List<SysSystemCount> selectSysSystemCountList(int precision);
}
