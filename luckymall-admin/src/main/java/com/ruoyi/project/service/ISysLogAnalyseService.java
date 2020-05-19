package com.ruoyi.project.service;

import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;

/**
 * 日志分析模块
 * @param
 * @return
 * @author zhenxing.dong
 */
public interface ISysLogAnalyseService {
    public void logRecent(String counterName, String message, String severity);

    public List<String> getRecentLogs(String counterName,String severity);

    public void logCommon(String counterName, String message, String severity, int timeout);

    public Set<Tuple> getCommonLogs(String counterName, String severity);

}
