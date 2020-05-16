package com.ruoyi.project.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.ruoyi.framework.util.RedisUtil;
import com.ruoyi.project.domain.SysProductType;
import com.ruoyi.project.mapper.SysProductMapper;
import com.ruoyi.project.mapper.SysProductTypeMapper;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.utils.Constant;
import com.ruoyi.system.utils.FileUploadUtil;
import com.ruoyi.system.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.domain.SysProduct;
import com.ruoyi.project.service.ISysProductService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * redis工具类
     */
    @Autowired
    private RedisUtil redisUtil;

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

        ModelAndView modelAndView = new ModelAndView("project/product/category");
        List<SysProduct> productList = sysProductMapper.findProductByType(id);
        modelAndView.addObject("productTypeName", name);
        modelAndView.addObject("list", productList);
        LOGGER.info("查找的商品种类：" + JSON.toJSONString(name));
        LOGGER.info("查询结果列表：" + JSON.toJSONString(productList));
        return modelAndView;
    }

    @Override
    public ModelAndView findProductByKey(String key) {
        LOGGER.info("===============按种类查找商品==============");
        ModelAndView modelAndView = new ModelAndView("project/product/category");
        List<SysProduct> productList = sysProductMapper.findProductByName(key);
        modelAndView.addObject("productTypeName", key);
        modelAndView.addObject("list", productList);
        LOGGER.info("查询条件及结果：" + JSON.toJSONString(key) + " " + JSON.toJSONString(productList));
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
     * 获取好评前五十的商品
     * @return
     */
    @Override
    public List<Map<String, String>> getHightCommentProducts() {
        //获取分值前50的商品id
        Set<String> ids = redisUtil.zrevrange("score:", 0, 50);

        List<Map<String, String>> products = new ArrayList<Map<String, String>>();
        for (String id : ids) {
            Map<String, String> productData = redisUtil.hgetall(id,0);
            products.add(productData);
        }
        return products;
    }

    /**
     * 对商品进行点赞
     * @param id
     * @return
     */
    @Override
    public Result like(String id) {
        //redis中商品的成员名
        String product = "product:" + id;
        //一周前
        long cutoff = (System.currentTimeMillis() / 1000) - Constant.ONE_WEEK_IN_SECONDS;

        Result result = new Result();

        //获取当前用户
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        if (null == user){
            result.setMsg(Constant.NOUSER_MSG);
        }else {
            //若商品上架时间超过一周，不可点赞
            if (redisUtil.zscore("time:", product) < cutoff) {
                result.setMsg("overtime");
            }
            //取商品id
            String productId = product.substring(product.indexOf(':') + 1);
            //若已投票集合添加成功
            if (1 == redisUtil.sadd("voted:" + productId, String.valueOf(user.getUserId()))) {
                //增加分数432
                redisUtil.zincrby("score:", Constant.VOTE_SCORE, product);
                //更新点赞数
                redisUtil.hincrBy(product, "likeNum", 1);
                result.setMsg(Constant.SUCCESS_MSG);
            }else {
                result.setMsg("voted");
            }
        }
        return result;
    }

    /**
     * 新增商品
     * 
     * @param sysProduct 商品实体
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSysProduct(MultipartFile file, SysProduct sysProduct)
    {
        LOGGER.info("===============添加商品==============");
        LOGGER.info("商品信息：" + JSON.toJSONString(sysProduct));

        //获取当前用户
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");

        String imageUrl = FileUploadUtil.savaFile(file, Constant.PRODUCT_IMAGE_PATH);
        String productId = UUID.randomUUID().toString();
        sysProduct.setProductPhoto(imageUrl);
        sysProduct.setId(productId);

        //添加已点赞集合
        String voted = "voted:"+productId;
        redisUtil.sadd(voted,String.valueOf(user.getUserId()));
        redisUtil.expire(voted,Constant.ONE_WEEK_IN_SECONDS,0);

        long now = System.currentTimeMillis() / 1000;
        String product = "product:" + productId;
        HashMap<String, String> productData = new HashMap<String, String>();
        productData.put("productName", sysProduct.getProductName());
        productData.put("productPrice", String.valueOf(sysProduct.getProductPrice()));
        productData.put("productPhoto", imageUrl);
        productData.put("link", sysProduct.getId());
        productData.put("postTime", String.valueOf(now));
        productData.put("likeNum", "1");
        productData.put("addCarNum","0");
        productData.put("view","0");
        redisUtil.hmset(product, productData,0);
        //分值排序zset
        redisUtil.zadd("score:", now + Constant.VOTE_SCORE, product);
        //时间排序的zset
        redisUtil.zadd("time:", now, product);

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
