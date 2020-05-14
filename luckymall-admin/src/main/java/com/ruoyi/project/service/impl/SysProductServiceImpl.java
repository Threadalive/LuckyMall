package com.ruoyi.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.ruoyi.project.domain.SysProductType;
import com.ruoyi.project.mapper.SysProductMapper;
import com.ruoyi.project.mapper.SysProductTypeMapper;
import com.ruoyi.system.utils.Constant;
import com.ruoyi.system.utils.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.domain.SysProduct;
import com.ruoyi.project.service.ISysProductService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-04-16
 */
@Service
public class SysProductServiceImpl implements ISysProductService
{
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysProductServiceImpl.class);

    @Autowired
    private SysProductMapper sysProductMapper;

    @Autowired
    private SysProductTypeMapper sysProductTypeMapper;

    @Override
    public Map<SysProductType, List<SysProduct>> getProductByTypeMap() {
        // Map<商品种类,商品列表>
        Map<SysProductType, List<SysProduct>> productMap = new HashMap<>(8);
        List<SysProductType> productTypeList = sysProductTypeMapper.selectAllSysProductType();
        for (SysProductType productType : productTypeList) {
            List<SysProduct> productList = sysProductMapper.findProductByType(productType.getId());
            // 每种商品最多存4个
            if (productList.size() > 4) {
                productMap.put(productType, productList.subList(0, 4));
            } else {
                productMap.put(productType, productList);
            }
        }
        LOGGER.info("商品信息map：" + JSON.toJSONString(productMap));
        return productMap;
    }

    /**
     * 方法说明：方法说明：根据商品种类查找相关商品
     *
     * @param id   商品种类id
     * @param name 商品种类名
     * @return org.springframework.web.servlet.ModelAndView 返回商品视图
     */
    @Override
    public ModelAndView findProductByType(int id, String name) {
        LOGGER.info("===============按种类查找商品==============");

        ModelAndView modelAndView = new ModelAndView("product/category");
        List<SysProduct> productList = sysProductMapper.findProductByType(id);
        modelAndView.addObject("productTypeName", name);
        modelAndView.addObject("list", productList);
        LOGGER.info("查找的商品种类：" + JSON.toJSONString(name));
        LOGGER.info("查询结果列表：" + JSON.toJSONString(productList));
        return modelAndView;
    }
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysProduct selectSysProductById(String id)
    {
        return sysProductMapper.selectSysProductById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysProduct 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysProduct> selectSysProductList(SysProduct sysProduct)
    {
        return sysProductMapper.selectSysProductList(sysProduct);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysProduct 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysProduct(MultipartFile file, SysProduct sysProduct)
    {
        LOGGER.info("===============添加商品==============");
        LOGGER.info("商品信息：" + JSON.toJSONString(sysProduct));
        String imageUrl = FileUploadUtil.savaFile(file, Constant.PRODUCT_IMAGE_PATH);
        sysProduct.setProductPhoto(imageUrl);
        sysProduct.setId(UUID.randomUUID().toString());
        return sysProductMapper.insertSysProduct(sysProduct);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysProduct 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysProduct(MultipartFile file,SysProduct sysProduct)
    {
        LOGGER.info("===============编辑商品==============");
        LOGGER.info("商品信息：" + JSON.toJSONString(sysProduct));
        String imageUrl = FileUploadUtil.savaFile(file, Constant.PRODUCT_IMAGE_PATH);
        sysProduct.setProductPhoto(imageUrl);
        return sysProductMapper.updateSysProduct(sysProduct);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProductByIds(String ids)
    {
        return sysProductMapper.deleteSysProductByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysProductById(String id)
    {
        return sysProductMapper.deleteSysProductById(id);
    }
}
