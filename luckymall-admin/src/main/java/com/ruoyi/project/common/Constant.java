package com.ruoyi.project.common;

/**
 * @Description: 常量类
 * @Author: zhenxing.dong
 * @Date: 2020/5/1 16:40
 */
public class Constant {
    /**
     * 用户默认的头像地址
     */
    public static final String DEFAULT_IMAGE = "/image/user/default_image.jpg";

    /**
     * 请求处理的返回信息--成功
     */
    public static final String SUCCESS_MSG = "success";
    /**
     * 请求处理的返回信息--错误
     */
    public static final String ERROR_MSG = "error";
    /**
     * 请求处理的返回信息--用户未登录
     */
    public static final String NOUSER_MSG = "nouser";
    /**
     * 请求处理的返回信息--用户被禁用
     */
    public static final String DISABLED_MSG = "disable";

    /**
     * 上传图片存储目录--用户目录
     */
    public static final String USER_IMAGE_PATH = "user";
    /**
     * 上传图片存储目录--商城目录
     */
    public static final String MALL_IMAGE_PATH = "mall";
    /**
     * 上传图片存储目录--商品目录
     */
    public static final String PRODUCT_IMAGE_PATH = "product";

    /**
     * 时间格式
     */
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 购物车map初始大小
     */
    public static final int CART_MAP_CAPACITY = 50;

    /**
     * 订单详情map初始大小
     */
    public static final int ORDERITEM_MAP_CAPACITY = 50;

    /**
     * 积分百分比
     */
    public static final int SCORE_PERCENT = 100;

    /**
     * 抽一次奖所需积分数
     */
    public static final int SCORE_PER_TIME = 100;

}
