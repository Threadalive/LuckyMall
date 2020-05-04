package com.ruoyi.project.mapper;

import com.ruoyi.project.domain.SysProductType;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2020-05-04
 */
public interface SysProductTypeMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysProductType selectSysProductTypeById(Long id);

    public List<SysProductType> selectAllSysProductType();
    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysProductType 【请填写功能名称】
     * @return 【请填写功能名称】集合
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysProductTypeById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProductTypeByIds(String[] ids);
}
