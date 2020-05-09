package com.ruoyi.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sys_product
 * 
 * @author ruoyi
 * @date 2020-04-16
 */
public class SysProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商品ID */
    private String id;

    /** 商品名 */
    @Excel(name = "商品名")
    private String productName;

    /** 商品价格 */
    @Excel(name = "商品价格")
    private Double productPrice;

    /** 商品库存 */
    @Excel(name = "商品库存")
    private Long productCount;

    /** 商品状态：0：下架；1：上架 */
    @Excel(name = "商品状态：0：下架；1：上架")
    private Long productStatus;

    /** 商品类别 */
    @Excel(name = "商品类别")
    private Integer productType;

    /** 商品图片路径 */
    @Excel(name = "商品图片路径")
    private String productPhoto;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }
    public void setProductPrice(Double productPrice) 
    {
        this.productPrice = productPrice;
    }

    public Double getProductPrice() 
    {
        return productPrice;
    }
    public void setProductCount(Long productCount) 
    {
        this.productCount = productCount;
    }

    public Long getProductCount() 
    {
        return productCount;
    }
    public void setProductStatus(Long productStatus) 
    {
        this.productStatus = productStatus;
    }

    public Long getProductStatus()
    {
        return productStatus;
    }
    public void setProductType(Integer productType)
    {
        this.productType = productType;
    }

    public Integer getProductType()
    {
        return productType;
    }
    public void setProductPhoto(String productPhoto) 
    {
        this.productPhoto = productPhoto;
    }

    public String getProductPhoto() 
    {
        return productPhoto;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("productName", getProductName())
            .append("productPrice", getProductPrice())
            .append("productCount", getProductCount())
            .append("productStatus", getProductStatus())
            .append("productType", getProductType())
            .append("productPhoto", getProductPhoto())
            .toString();
    }
}
