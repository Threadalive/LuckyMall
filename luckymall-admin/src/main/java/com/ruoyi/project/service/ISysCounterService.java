package com.ruoyi.project.service;


import com.ruoyi.system.utils.Pair;

import java.util.List;

public interface ISysCounterService {

    public void updateCounter(String counterName);

    public void updateCounterDown(String counterName);

    public List<Pair<Integer, Integer>> getCounter(String counterName, int precision);
}
