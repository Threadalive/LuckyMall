package com.ruoyi.system.utils;

/**
 * @Description: 返回数据封装类
 * @Author: zhenxing.dong
 * @Date: 2020/5/1 9:30
 * @param <T> 数据域类
 */
public class Result<T> {
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 具体内容
     */
    private T data;

    /**
     * 方法说明：无参构造方法
     *
     */
    public Result() {
    }

    /**
     * 方法说明：构造方法
     * @param msg   消息
     * @param data  数据域
     */
    public Result(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
