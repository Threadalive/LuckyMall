package com.ruoyi.project.service.impl;

import com.ruoyi.framework.util.RedisUtil;
import com.ruoyi.project.service.ISysCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Transaction;
import com.ruoyi.system.utils.Pair;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2020/5/17 20:23
 */
@Service
public class SysCounterServiceImpl implements ISysCounterService {

    public static final SimpleDateFormat TIMESTAMP =
            new SimpleDateFormat("EEE MMM dd HH:00:00 yyyy");
    private static final SimpleDateFormat ISO_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00");

    //精度，以秒计数                                  1s 5s 1min 5min  1h    5h    24h    一周
    public static final int[] PRECISION = new int[]{1, 5, 60, 300, 3600, 18000, 86400, 604800};

    @Autowired
    private RedisUtil redisUtil;

    static {
        ISO_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * 根据计数器名和精度取数据
     *
     * @param counterName 计数器名
     * @param precision   精度
     * @return
     */
    @Override
    public List<Pair<Integer, Integer>> getCounter(String counterName, int precision) {
        String hash = String.valueOf(precision) + ':' + counterName;
        //获取 count:1:counterName 的hash下数据
        Map<String, String> data = redisUtil.hgetall("count:" + hash, 0);
        ArrayList<Pair<Integer, Integer>> results = new ArrayList<>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            results.add(new Pair<Integer, Integer>(
                    Integer.parseInt(entry.getKey()),
                    Integer.parseInt(entry.getValue())) {
            });
        }
        return results;
    }

    /**
     * 更新计数器+1
     *
     * @param countName 计数器名
     */
    @Override
    public void updateCounter(String countName) {
        long now = System.currentTimeMillis() / 1000;
        //执行多条命令
        Transaction trans = redisUtil.multi();
        for (int prec : PRECISION) {
            //取整精度时
            long pnow = (now / prec) * prec;
            String hash = String.valueOf(prec) + ':' + countName;
            //记录现有计数器
            trans.zadd("knownCounter:", 0, hash);
            //更新计数
            trans.hincrBy("count:" + hash, String.valueOf(pnow), 1);
        }
        trans.exec();
    }

    /**
     * 更新计数器-1
     *
     * @param counterName
     */
    @Override
    public void updateCounterDown(String counterName) {
        long now = System.currentTimeMillis() / 1000;
        //执行多条命令
        Transaction trans = redisUtil.multi();
        for (int prec : PRECISION) {
            //取整精度时
            long pnow = (now / prec) * prec;
            String hash = String.valueOf(prec) + ':' + counterName;
            //记录现有计数器
            trans.zadd("knownCounter:", 0, hash);
            //更新计数
            trans.hincrBy("count:" + hash, String.valueOf(pnow), -1);
        }
        trans.exec();
    }
}
