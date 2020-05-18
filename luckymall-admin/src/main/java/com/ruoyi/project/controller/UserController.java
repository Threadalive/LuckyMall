package com.ruoyi.project.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.ShiroConstants;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.shiro.session.OnlineSession;
import com.ruoyi.framework.util.RedisUtil;
import com.ruoyi.project.service.ISysCounterService;
import com.ruoyi.project.service.ISysOrderService;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.utils.Constant;
import com.ruoyi.system.utils.Result;
import org.apache.shiro.authc.DisabledAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description: 用户控制层
 * @Author: zhenxing.dong
 *
 * @Date: 2019/8/2 9:46
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private String PREFIX = "project/user";

    /**
     * 用户登录次数计数 redisKey 前缀
     */
    private String SHIRO_LOGIN_COUNT = "shiro_login_count_";

    /**
     * 用户登录是否被锁定5分钟 redisKey 前缀
     */
    private String SHIRO_IS_LOCK = "shiro_is_lock_";

    /**
     * 最大尝试次数3
     */
    private Integer MAX_RETRY_COUNT = 3;
    /**
     * 用户服务层
     */
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysCounterService counterService;
    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 订单服务层
     */
    @Autowired
    private ISysOrderService sysOrderService;

    @Autowired
    private RedisUtil redisUtil;
    /**
     * 方法说明：跳转登录界面
     *
     * @return 登录界面地址
     */
    @GetMapping("/login")
    public String login() {
        return PREFIX+"/login";
    }
    /**
     * 方法说明：用户登录验证
     *
     * @param userName 用户名
     * @param password 密码
     * @return com.luckymall.common.Result  返回验证信息 success/验证成功 error/验证失败
     */
    @PostMapping("/login")
    @ResponseBody
    public Result loginUser(String userName, String password) {
        Result result = new Result();
        //根据用户名记录尝试登录次数
        redisUtil.incr(SHIRO_LOGIN_COUNT+userName);
        //若尝试次数大于限定值
        if (Integer.parseInt(redisUtil.get(SHIRO_LOGIN_COUNT+userName,0)) >= MAX_RETRY_COUNT){
            //记录锁定标记，5分钟后过期,删除两项记录
            redisUtil.set(SHIRO_IS_LOCK+userName,"LOCKED",0);
            redisUtil.expire(SHIRO_IS_LOCK+userName,300,0);
            redisUtil.expire(SHIRO_LOGIN_COUNT+userName,300,0);
        }
        // 根据用户名和密码查找用户
        result = userService.loginUser(userName, password);

        //登陆成功，则删除标记,记录登录信息
        if (Constant.SUCCESS_MSG.equals(result.getMsg())){
            redisUtil.del(SHIRO_LOGIN_COUNT+userName);
            redisUtil.del(SHIRO_IS_LOCK+userName);
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
//            //获取记录在线用户信息
//            OnlineSession session = (OnlineSession) request.getAttribute(ShiroConstants.ONLINE_SESSION);
//            AsyncManager.me().execute(AsyncFactory.syncSessionToDb(session));
            counterService.updateCounter(Constant.ONLINE_USER_COUNTER);
        }
        if ("LOCKED".equals(redisUtil.get(SHIRO_IS_LOCK+userName,0))){
            result.setMsg("locked");
        }
        return result;
    }

    /**
     * 方法说明：用户注册验证
     *
     * @param user 用户
     * @return com.luckymall.common.Result  success/注册成功 error/注册失败
     */
    @PostMapping("/registerUser")
    @ResponseBody
    public Result register(SysUser user) {
        Result result = new Result();
        HttpSession session = request.getSession();
        boolean flag = userService.registerUser(user);
        if (flag == false) {
            result.setMsg(Constant.ERROR_MSG);
            return result;
        }
        counterService.updateCounter(Constant.REGISTER_COUNTER);
        result.setMsg(Constant.SUCCESS_MSG);
        session.setAttribute("user", user);
        return result;
    }
    /**
     * 方法说明：跳转注册界面
     *
     * @return 注册界面地址
     */
    @GetMapping("/registerUser")
    public String register() {
        return PREFIX+"/register";
    }

    /**
     * 方法说明：跳转用户信息修改页面
     *
     * @return 用户信息修改页面
     */
    @GetMapping("/editUser")
    public String userEdit() {
        return PREFIX+"/editUser";
    }
    /**
     * 方法说明：用户信息修改
     *
     * @param file 上传的图片
     * @param user 用户
     * @return com.luckymall.common.Result 返回验证信息
     */
    @PostMapping("/editUser")
    @ResponseBody
    public Result editUser(MultipartFile file, SysUser user) {
        Result result = new Result();
        result = userService.updateUser(file, user);
        return result;
    }

    /**
     * 方法说明：跳转用户密码修改页面
     *
     * @return 用户密码修改页面
     */
    @GetMapping("/editPassword")
    public String passwordEdit() {
        return PREFIX+"/editPassword";
    }

    /**
     * 方法说明：修改用户密码
     *
     * @param oldPassword 旧密码
     * @param password    新密码
     * @return com.luckymall.common.Result  返回验证信息
     */
    @PostMapping("/editPassword")
    @ResponseBody
    public Result editPassword(String oldPassword, String password) {
        Result result;
        result = userService.editPassword(oldPassword, password);
        return result;
    }

    /**
     * 方法说明：用户查看订单详情页
     *
     * @param orderId 订单id
     * @return org.springframework.web.servlet.ModelAndView 订单详情页视图
     */
    @RequestMapping("/orderDetail")
    public ModelAndView userOrderDetail(String orderId) {
        ModelAndView modelAndView = sysOrderService.userOrderDetail(orderId);
        return modelAndView;
    }
}
