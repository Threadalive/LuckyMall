package com.ruoyi.web.controller.tool;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2020/5/17 23:31
 */

import com.ruoyi.framework.util.RedisUtil;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 清理计数器守护进程
 */
public class CleanCountersThread extends Thread {
    private  RedisUtil redisUtil;
    private int sampleCount = 100;
    private boolean quit;
    /**
     * used to mimic a time in the future.
     */
    private long timeOffset;

    public CleanCountersThread(RedisUtil redisUtil,int sampleCount, long timeOffset) {
        this.redisUtil = redisUtil;
        this.sampleCount = sampleCount;
        this.timeOffset = timeOffset;
    }
    public void quit(){
        quit = true;
    }

    @Override
    public void run(){
        int passes = 0;
        while (!quit){
            long start = System.currentTimeMillis() + timeOffset;
            int index = 0;
            while (index < redisUtil.zcard("knownCounter:")){
                Set<String> hashSet = redisUtil.zrange("knownCounter:", index, index);
                index++;
                if (hashSet.size() == 0) {
                    break;
                }
                String hash = hashSet.iterator().next();
                int prec = Integer.parseInt(hash.substring(0, hash.indexOf(':')));
                int bprec = (int)Math.floor(prec / 60);
                //精度在1分钟以内
                if (bprec == 0){
                    bprec = 1;
                }
                if ((passes % bprec) != 0){
                    continue;
                }
                String hkey = "count:" + hash;
                String cutoff = String.valueOf(
                        ((System.currentTimeMillis() + timeOffset) / 1000) - sampleCount * prec);
                ArrayList<String> samples = new ArrayList<String>(redisUtil.hkeys(hkey));
                Collections.sort(samples);
                int remove = bisectRight(samples, cutoff);
                if (remove != 0){
                    redisUtil.hdel(hkey, samples.subList(0, remove).toArray(new String[0]));
                    if (remove == samples.size()){
                        redisUtil.watch(hkey);
                        if (redisUtil.hlen(hkey) == 0) {
                            Transaction trans = redisUtil.multi();
                            trans.zrem("knownCounter:", hash);
                            trans.exec();
                            index--;
                        }else{
                            redisUtil.unWatch();
                        }
                    }
                }
            }
            passes++;
            long duration = Math.min(
                    (System.currentTimeMillis() + timeOffset) - start + 1000, 60000);
            try {
                sleep(Math.max(60000 - duration, 1000));
            }catch(InterruptedException ie){
                Thread.currentThread().interrupt();
            }
        }
    }
    // mimic python's bisect.bisect_right
    public int bisectRight(List<String> values, String key) {
        int index = Collections.binarySearch(values, key);
        return index < 0 ? Math.abs(index) - 1 : index + 1;
    }
}
