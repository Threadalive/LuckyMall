package com.ruoyi.project.controller;

import com.ruoyi.framework.util.RedisUtil;
import com.ruoyi.project.service.ISysCounterService;
import com.ruoyi.project.service.ISysLogAnalyseService;
import com.ruoyi.project.service.ISysProductService;
import com.ruoyi.system.utils.Constant;
import com.ruoyi.web.controller.tool.CleanCountersThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2020/4/14 14:30
 */
@Controller
public class MallIndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MallIndexController.class);
    /**
     * 后台计数服务
     */
    @Autowired
    private ISysCounterService counterService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysLogAnalyseService logAnalyseService;

    private final String PREFIX = "project/";

    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysProductService productService;

    /**
     * 系统首页
     *
     * @param mmap
     * @return
     */
    @GetMapping("/mallIndex")
    public String index(ModelMap mmap) {
        //用户访问，进行系统访问计数
        counterService.updateCounter(Constant.ACCESS_COUNT_COUNTER);
//        //频繁日志记录
//        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "user access the index of mall", Constant.INFO, Constant.LOG_TIMEOUT);
        // 用户访问系统即开启清理计数器的守护进程
        CleanCountersThread thread = new CleanCountersThread(redisUtil, 100, 5);
        thread.start();
        //放入商品信息
        mmap.put("map", productService.getProductByTypeMap());
        return PREFIX + "index/index";
    }

    /**
     * 方法说明：跳转用户个人中心页面
     *
     * @return 个人中心地址
     */
    @RequestMapping("/userIndex")
    public String userIndex() {
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "user access the user's homepage", Constant.INFO, Constant.LOG_TIMEOUT);
        return PREFIX + "user/userIndex";
    }

    /**
     * 方法说明：用户退出登录
     *
     * @return 返回商城首页
     */
    @GetMapping("/user/logout")
    public String logout() {
        LOGGER.info("===============用户退出==============");
        //频繁日志记录
        logAnalyseService.logCommon(Constant.CURRENCY_LOG, "user logout", Constant.INFO, Constant.LOG_TIMEOUT);
        request.getSession().invalidate();
        //在线用户数-1
        counterService.updateCounterDown(Constant.ONLINE_USER_COUNTER);
        return "redirect:/mallIndex";
    }
}
