package com.ruoyi.system.utils;

/**
 * @Description: 常量类
 * @Author: zhenxing.dong
 * @Date: 2020/5/1 16:40
 */
public class Constant {

    public static final String DEBUG = "debug";
    public static final String INFO = "info";
    public static final String WARNING = "warning";
    public static final String ERROR = "error";
    public static final String CRITICAL = "critical";
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

    /**
     * 每周秒数
     */
    public static final int ONE_WEEK_IN_SECONDS = 7 * 86400;
    /**
     * 点赞得分
     */
    public static final int VOTE_SCORE = 432;

    /**
     * 通过购物车添加的订单
     */
    public static final String ADD_BY_CAR_KEY = "add_by_car";

    /**
     * 成交额
     */
    public static final String TURNOVER = "turnover";

    /**
     * 订单数
     */
    public static final String ORDER_COUNT = "order_count";

    /**
     * 系统访问次数计数器
     */
    public static final String ACCESS_COUNT_COUNTER = "sys_access_count_counter";

    /**
     * 注册用户计数器
     */
    public static final String REGISTER_COUNTER = "register_count_counter";

    /**
     * 计数器记录订单数
     */
    public static final String ORDER_COUNT_BY_TIME = "order_count_time";

    /**
     * 在线用户数
     */
    public static final String ONLINE_USER_COUNTER = "online_user_counter";

    /**
     * 硬盘读写次数
     */
    public static final String DISK_READ_COUNTER = "disk_read_counter";

    /**
     * 图片文件基础路径
     */
    public static final String IMG_FILE_PATH = "/Users/mac/Documents/航天班/毕业设计/LuckyMall/luckymall-admin/src/main/resources/static/";

    /**
     * 通用日志记录器
     */
    public static final String CURRENCY_LOG = "currency_log";

    /**
     * 5s时延
     */
    public static final Integer LOG_TIMEOUT = 5000;

}
