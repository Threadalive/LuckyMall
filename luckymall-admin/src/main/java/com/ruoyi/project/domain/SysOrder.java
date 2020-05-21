package com.ruoyi.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * 订单对象 sys_order
 *
 * @author zhenxing.dong
 * @date 2020-05-06
 */
public class SysOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private String id;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String orderCode;

    /**
     * 订单状态
     */
    @Excel(name = "订单状态")
    private Integer status;

    @Excel(name = "订单创建时间")
    private String createTime;

    /**
     * 订单总金额
     */
    @Excel(name = "订单总金额")
    private Double totalPrice;

    /**
     * 订单用户
     */
    @Excel(name = "订单用户")
    private Long userId;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderCode", getOrderCode())
                .append("createTime", getCreateTime())
                .append("status", getStatus())
                .append("totalPrice", getTotalPrice())
                .append("userId", getUserId())
                .toString();
    }
}
