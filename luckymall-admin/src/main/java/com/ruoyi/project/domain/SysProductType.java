package com.ruoyi.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sys_product_type
 * 
 * @author ruoyi
 * @date 2020-05-04
 */
public class SysProductType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商品种类id */
    private Integer id;

    /** 商品种类名 */
    @Excel(name = "商品种类名")
    private String name;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
