package com.ruoyi.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * counter对象 sys_system_count
 *
 * @author zhenxing.dong
 * @date 2020-05-18
 */
public class SysSystemCount extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * null
     */
    private String id;

    /**
     * 系统访问次数
     */
    @Excel(name = "系统访问次数")
    private Long accessCount;

    /**
     * 成交额
     */
    @Excel(name = "成交额")
    private Double turnover;

    /**
     * 订单数量
     */
    @Excel(name = "订单数量")
    private Long orderCount;

    /**
     * 注册用户 数
     */
    @Excel(name = "注册用户 数")
    private Long registerCount;

    /**
     * 同时在线用户数
     */
    @Excel(name = "同时在线用户数")
    private Long onlineUserCount;

    /**
     * 系统硬盘读取次数
     */
    @Excel(name = "系统硬盘读取次数")
    private Long diskReadCount;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAccessCount(Long accessCount) {
        this.accessCount = accessCount;
    }

    public Long getAccessCount() {
        return accessCount;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setRegisterCount(Long registerCount) {
        this.registerCount = registerCount;
    }

    public Long getRegisterCount() {
        return registerCount;
    }

    public void setOnlineUserCount(Long onlineUserCount) {
        this.onlineUserCount = onlineUserCount;
    }

    public Long getOnlineUserCount() {
        return onlineUserCount;
    }

    public void setDiskReadCount(Long diskReadCount) {
        this.diskReadCount = diskReadCount;
    }

    public Long getDiskReadCount() {
        return diskReadCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("accessCount", getAccessCount())
                .append("turnover", getTurnover())
                .append("orderCount", getOrderCount())
                .append("registerCount", getRegisterCount())
                .append("onlineUserCount", getOnlineUserCount())
                .append("diskReadCount", getDiskReadCount())
                .toString();
    }
}
