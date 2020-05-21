package com.ruoyi.project.service;

import java.util.List;

import com.ruoyi.project.domain.SysProductType;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2020-05-04
 */
public interface ISysProductTypeService {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysProductType selectSysProductTypeById(Long id);

    /**
     * 方法说明：获取商品类别列表
     *
     * @return org.springframework.web.servlet.ModelAndView 商品类别管理视图
     */
    public List<SysProductType> selectSysProductTypeList(SysProductType sysProductType);

    /**
     * 新增【请填写功能名称】
     *
     * @param sysProductType 【请填写功能名称】
     * @return 结果
     */
    public int insertSysProductType(SysProductType sysProductType);

    /**
     * 修改【请填写功能名称】
     *
     * @param sysProductType 【请填写功能名称】
     * @return 结果
     */
    public int updateSysProductType(SysProductType sysProductType);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProductTypeByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysProductTypeById(Long id);
}
