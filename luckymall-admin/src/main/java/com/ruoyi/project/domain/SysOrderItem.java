package com.ruoyi.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单项对象 sys_order_item
 *
 * @author zhenxing.dong
 * @date 2020-05-14
 */
public class SysOrderItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 订单项id
     */
    private String id;

    /**
     * 订单中的商品项
     */
    @Excel(name = "订单中的商品项")
    private String productId;

    /**
     * 关联商品数量
     */
    @Excel(name = "关联商品数量")
    private Integer productNum;

    /**
     * 关联订单id
     */
    @Excel(name = "关联订单id")
    private String orderId;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("productId", getProductId())
                .append("productNum", getProductNum())
                .append("orderId", getOrderId())
                .toString();
    }
}
