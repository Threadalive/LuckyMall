package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.framework.util.RedisUtil;
import com.ruoyi.project.service.ISysCounterService;
import com.ruoyi.system.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysMenu;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.utils.Pair;

/**
 * 首页 业务处理
 * 
 * @author ruoyi
 */
@Controller
public class SysIndexController extends BaseController
{
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysCounterService counterService;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        SysUser user = ShiroUtils.getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("sideTheme", configService.selectConfigByKey("sys.index.sideTheme"));
        mmap.put("skinName", configService.selectConfigByKey("sys.index.skinName"));
        mmap.put("copyrightYear", Global.getCopyrightYear());
        mmap.put("demoEnabled", Global.isDemoEnabled());
        return "index";
    }

    // 切换主题
    @GetMapping("/system/switchSkin")
    public String switchSkin(ModelMap mmap)
    {
        return "skin";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        mmap.put("version", Global.getVersion());
        return "main";
    }
    @GetMapping("/system/main_v1")
    public String mainV1(ModelMap modelMap)
    {
        String turnover = "0";
        if (null != redisUtil.get(Constant.TURNOVER,0)){
            turnover = redisUtil.get(Constant.TURNOVER,0);
        }
        String orderCount = "0";
        if (null != redisUtil.get(Constant.ORDER_COUNT,0)){
            orderCount = redisUtil.get(Constant.ORDER_COUNT,0);
        }
        int accessCount = 0;
        //取一天范围内的访问数据
        List<Pair<Integer, Integer>> counter = counterService.getCounter(Constant.ACCESS_COUNT_COUNTER,86400);
        for (Pair<Integer, Integer> count : counter) {
            accessCount += count.getValue();
        }
        modelMap.put("accessCount",accessCount);
        modelMap.put("turnover",turnover);
        modelMap.put("orderCount",orderCount);

        return "main_v1";
    }
}
