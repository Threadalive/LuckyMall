package com.ruoyi.project.controller;

import com.ruoyi.project.service.ISysOrderService;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.utils.Constant;
import com.ruoyi.system.utils.Result;
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
     * 用户服务层
     */
    @Autowired
    private ISysUserService userService;
    /**
     * 购物车服务层
     */
//    @Autowired
//    private CartService cartService;

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

//    /**
//     * 方法说明：用户查看购物车
//     *
//     * @return org.springframework.web.servlet.ModelAndView 购物车视图
//     */
//    @RequestMapping("/cart")
//    public ModelAndView userCart() {
//        ModelAndView modelAndView = cartService.userCart();
//        return modelAndView;
//    }

//    /**
////     * 方法说明：用户查看订单
////     *
////     * @return org.springframework.web.servlet.ModelAndView 用户订单视图
////     */
//    @RequestMapping("/order")
//    public ModelAndView userOrder() {
//        ModelAndView modelAndView = sysOrderService.getUserOrder();
//        return modelAndView;
//    }

    /**
     * 方法说明：用户查看订单详情页
     *
     * @param orderId 订单id
     * @return org.springframework.web.servlet.ModelAndView 订单详情页视图
     */
    @RequestMapping("/orderDetail")
    public ModelAndView userOrderDetail(Long orderId) {
        ModelAndView modelAndView = sysOrderService.userOrderDetail(orderId);
        return modelAndView;
    }

//    /**
//     * 方法说明：用户查看积分明细
//     *
//     * @return org.springframework.web.servlet.ModelAndView 积分视图
//     */
//    @RequestMapping("/score")
//    public ModelAndView userScore() {
//        ModelAndView modelAndView = scoreRecordService.userScore();
//        return modelAndView;
//    }

//    /**
//     * 方法说明：用户积分抽奖
//     *
//     * @return org.springframework.web.servlet.ModelAndView 积分抽奖视图
//     */
//    @RequestMapping("/lottery")
//    public ModelAndView userLottery(){
//        ModelAndView modelAndView =scoreRecordService.userLottery();
//        return modelAndView;
//    }

//    /**
//     * 方法说明：抽奖结果处理
//     * @param product 抽中的商品
//     * @return com.luckymall.common.Result 结果
//     */
//    @RequestMapping("/doLottery")
//    @ResponseBody
//    public Result doLottery(Product product){
//        Result result =scoreRecordService.doLottery(product);
//        return result;
//    }

//    /**
//     * 方法说明：管理员添加会员
//     *
//     * @param user 会员
//     * @return com.luckymall.common.Result 结果
//     */
//    @RequestMapping("/admin/add")
//    @ResponseBody
//    public Result addUser(User user) {
//        Result result = userService.addUser(user);
//        return result;
//    }

//    /**
//     * 方法说明：管理员修改会员状态
//     *
//     * @param id     会员id
//     * @param status 会员状态 0/已禁用 1/已启用
//     * @return com.luckymall.common.Result  结果
//     */
//    @RequestMapping("/admin/editUserStatus")
//    @ResponseBody
//    public Result editUserStatus(int id, int status) {
//        Result result = userService.editUserStatus(id,status);
//        return result;
//    }
//
//    /**
//     * 方法说明：管理员修改会员信息
//     * @param file  会员头像信息
//     * @param user 会员信息
//     * @return com.luckymall.common.Result 结果
//     */
//    @RequestMapping("/admin/edit")
//    @ResponseBody
//    public Result editUserByAdmin(MultipartFile file, User user){
//        Result result=userService.editUserByAdmin(file,user);
//        return result;
//    }
//
//    /**
//     * 方法说明：管理员修改会员密码
//     * @param username  会员名
//     * @param password  密码
//     * @return com.luckymall.common.Result 结果
//     */
//    @RequestMapping("/admin/editPassword")
//    @ResponseBody
//    public Result editPasswordByAdmin(String username, String password){
//        Result result =userService.editPasswordByAdmin(username,password);
//        return result;
//    }
}
