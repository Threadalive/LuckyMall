package com.ruoyi.project.service.impl;

import com.ruoyi.framework.util.RedisUtil;
import com.ruoyi.project.domain.SysSystemCount;
import com.ruoyi.project.service.ISysCounterService;
import com.ruoyi.system.utils.Constant;
import com.ruoyi.system.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.service.ISysSystemCountService;

import java.util.ArrayList;
import java.util.List;

/**
 * counterService业务层处理
 *
 * @author zhenxing.dong
 * @date 2020-05-18
 */
@Service
public class SysSystemCountServiceImpl implements ISysSystemCountService {
    @Autowired
    private ISysCounterService counterService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<SysSystemCount> selectSysSystemCountList(int precision) {

        List<SysSystemCount> countList = new ArrayList<>();
        SysSystemCount systemCount = new SysSystemCount();
        long accessCount = 0;
        double turnover = 0;
        long orderCount = 0;
        long registerCount = 0;
        long onlineUserCount = 0;
        long diskReadCount = 0;

        //取精度范围内的访问数据
        List<Pair<Integer, Integer>> accessCounter = counterService.getCounter(Constant.ACCESS_COUNT_COUNTER, precision);
        for (Pair<Integer, Integer> count : accessCounter) {
            accessCount += count.getValue();
        }

        //总成交额
        if (null != redisUtil.get(Constant.TURNOVER, 0)) {
            turnover = Double.parseDouble(redisUtil.get(Constant.TURNOVER, 0));
        }

        //取精度范围内的订单量
        List<Pair<Integer, Integer>> orderCounter = counterService.getCounter(Constant.ORDER_COUNT_BY_TIME, precision);
        for (Pair<Integer, Integer> count : orderCounter) {
            orderCount += count.getValue();
        }

        //取精度范围内的注册量
        List<Pair<Integer, Integer>> registerCounter = counterService.getCounter(Constant.REGISTER_COUNTER, precision);
        for (Pair<Integer, Integer> count : registerCounter) {
            registerCount += count.getValue();
        }

        //取精度范围内的注册量
        List<Pair<Integer, Integer>> onlineUserCounter = counterService.getCounter(Constant.ONLINE_USER_COUNTER, precision);
        for (Pair<Integer, Integer> count : onlineUserCounter) {
            onlineUserCount += count.getValue();
        }

        //取精度范围内的注册量
        List<Pair<Integer, Integer>> diskReadCounter = counterService.getCounter(Constant.DISK_READ_COUNTER, precision);
        for (Pair<Integer, Integer> count : diskReadCounter) {
            diskReadCount += count.getValue();
        }

        systemCount.setAccessCount(accessCount);
        systemCount.setTurnover(turnover);
        systemCount.setOrderCount(orderCount);
        systemCount.setRegisterCount(registerCount);
        systemCount.setOnlineUserCount(onlineUserCount);
        systemCount.setDiskReadCount(diskReadCount);

        countList.add(systemCount);
        return countList;
    }
}
