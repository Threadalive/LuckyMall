package com.ruoyi.project.service.impl;

import com.ruoyi.framework.util.RedisUtil;
import com.ruoyi.project.service.ISysLogAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

/**
 * @Description 日志收集分析模块
 * @Author zhenxing.dong
 * @Date 2020/5/19 17:08
 */
@Service
public class SysLogAnalyseServiceImpl implements ISysLogAnalyseService {

    public static final SimpleDateFormat TIMESTAMP =
            new SimpleDateFormat("EEE MMM dd HH:00:00 yyyy");
    private static final SimpleDateFormat ISO_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00");

    static {
        ISO_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static final Collator COLLATOR = Collator.getInstance();

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 收集最近100条日志
     *
     * @param counterName 计数器名
     * @param message     日志信息
     * @param severity    日志级别
     */
    @Override
    public void logRecent(String counterName, String message, String severity) {
        String destination = "recent:" + counterName + ':' + severity;
//        Pipeline pipe = redisUtil.pipelined();
        redisUtil.lpush(0, destination, TIMESTAMP.format(new Date()) + ' ' + message);
        redisUtil.ltrim(destination, 0, 99);
//        pipe.sync();
    }

    /**
     * 取最近日志
     *
     * @param counterName
     * @param severity
     * @return
     */
    @Override
    public List<String> getRecentLogs(String counterName, String severity) {
        String recentLogsKey = "recent:" + counterName + ":" + severity;
        return redisUtil.lrange(recentLogsKey, 0, -1, 0);
    }

    /**
     * 记录频繁日志，同时插入最近日志
     *
     * @param counterName
     * @param message
     * @param severity
     * @param timeout
     */
    @Override
    public void logCommon(String counterName, String message, String severity, int timeout) {
        String commonDest = "common:" + counterName + ':' + severity;
        String startKey = commonDest + ":start";
        long end = System.currentTimeMillis() + timeout;
        while (System.currentTimeMillis() < end) {
            redisUtil.watch(startKey);
            String hourStart = ISO_FORMAT.format(new Date());

            //续存时间
            String existing = redisUtil.get(startKey, 0);

            Transaction trans = redisUtil.multi();
            if (existing != null && COLLATOR.compare(existing, hourStart) < 0) {
                trans.rename(commonDest, commonDest + ":last");
                trans.rename(startKey, commonDest + ":pstart");
                trans.set(startKey, hourStart);
            }

            trans.zincrby(commonDest, 1, message);

            String recentDest = "recent:" + counterName + ':' + severity;
            trans.lpush(recentDest, TIMESTAMP.format(new Date()) + ' ' + message);
            trans.ltrim(recentDest, 0, 99);
            List<Object> results = trans.exec();
            // null response indicates that the transaction was aborted due to
            // the watched key changing.
            if (results == null) {
                continue;
            }
            return;
        }
    }

    @Override
    public Set<Tuple> getCommonLogs(String counterName, String severity) {
        String commonLogsKeys = "common:" + counterName + ":" + severity;
        return redisUtil.zrevrangeWithScores(commonLogsKeys, 0, -1);
    }
}
