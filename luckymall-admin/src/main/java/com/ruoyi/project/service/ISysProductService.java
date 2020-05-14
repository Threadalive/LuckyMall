package com.ruoyi.project.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.domain.SysProduct;
import com.ruoyi.project.domain.SysProductType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zhenxing.dong
 * @date 2020-04-16
 */
public interface ISysProductService 
{

    public Map<SysProductType,List<SysProduct>> getProductByTypeMap();

    public ModelAndView findProductByType(int id, String name);
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysProduct selectSysProductById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysProduct 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysProduct> selectSysProductList(SysProduct sysProduct);

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysProduct 【请填写功能名称】
     * @return 结果
     */
    public int insertSysProduct(MultipartFile file,SysProduct sysProduct);

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysProduct 【请填写功能名称】
     * @return 结果
     */
    public int updateSysProduct(MultipartFile file,SysProduct sysProduct);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProductByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysProductById(String id);
}
