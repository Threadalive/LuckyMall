package com.ruoyi.project.controller;

import com.ruoyi.project.service.ISysProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2020/4/14 14:30
 */
@Controller
public class MallIndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MallIndexController.class);

    private final String PREFIX = "project/index/";

    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysProductService productService;
    // 系统首页
    @GetMapping("/mallIndex")
    public String index(ModelAndView mmap)
    {
        //放入商品信息
        mmap.addObject("map",productService.getProductByTypeMap());
        return PREFIX+"index";
    }

    /**
     * 方法说明：用户退出登录
     *
     * @return 返回商城首页
     */
    @RequestMapping("/logout")
    public String logout() {
        LOGGER.info("===============用户退出==============");
        request.getSession().invalidate();
        return "redirect:/mallIndex";
    }
}
