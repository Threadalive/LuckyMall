package com.ruoyi.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 购物车对象 sys_shopping_car
 *
 * @author zhenxing.dong
 * @date 2020-05-08
 */
public class SysShoppingCar extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 购物车id
     */
    private String id;

    /**
     * 添加时间
     */
    @Excel(name = "添加时间")
    private String addTime;

    /**
     * 商品数量
     */
    @Excel(name = "商品数量")
    private Long number;

    /**
     * 对应商品id
     */
    @Excel(name = "对应商品id")
    private String productId;

    /**
     * 对应用户id
     */
    @Excel(name = "对应用户id")
    private Long userId;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getNumber() {
        return number;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
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
                .append("addTime", getAddTime())
                .append("number", getNumber())
                .append("productId", getProductId())
                .append("userId", getUserId())
                .toString();
    }
}
