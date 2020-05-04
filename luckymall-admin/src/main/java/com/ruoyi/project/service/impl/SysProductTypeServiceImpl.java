package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.domain.SysProductType;
import com.ruoyi.project.mapper.SysProductTypeMapper;
import com.ruoyi.project.service.ISysProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-05-04
 */
@Service
public class SysProductTypeServiceImpl implements ISysProductTypeService
{
    @Autowired
    private SysProductTypeMapper sysProductTypeMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysProductType selectSysProductTypeById(Long id)
    {
        return sysProductTypeMapper.selectSysProductTypeById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysProductType 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysProductType> selectSysProductTypeList(SysProductType sysProductType)
    {
        return sysProductTypeMapper.selectSysProductTypeList(sysProductType);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysProductType 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysProductType(SysProductType sysProductType)
    {
        return sysProductTypeMapper.insertSysProductType(sysProductType);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysProductType 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysProductType(SysProductType sysProductType)
    {
        sysProductType.setUpdateTime(DateUtils.getNowDate());
        return sysProductTypeMapper.updateSysProductType(sysProductType);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProductTypeByIds(String ids)
    {
        return sysProductTypeMapper.deleteSysProductTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysProductTypeById(Long id)
    {
        return sysProductTypeMapper.deleteSysProductTypeById(id);
    }
}
