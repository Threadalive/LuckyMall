package com.ruoyi.web.controller.tool;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.ruoyi.framework.util.RedisUtil;
import com.ruoyi.project.domain.SysOrder;
import com.ruoyi.project.domain.SysOrderItem;
import com.ruoyi.project.domain.SysProduct;
import com.ruoyi.project.mapper.SysOrderItemMapper;
import com.ruoyi.project.mapper.SysOrderMapper;
import com.ruoyi.project.service.IEmailService;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.utils.Constant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2020/5/20 11:19
 */
@Component
public class KafkaListenerComponent {

    @Autowired
    private SysOrderMapper orderMapper;

    @Autowired
    private SysOrderItemMapper orderItemMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private IEmailService emailService;

    @KafkaListener(topics = "insertOrder",groupId ="g1" )
    public void insertOrder(ConsumerRecord<?, String> consumerRecord) {
        String value = consumerRecord.value();
        SysOrder order = JSON.parseObject(value,SysOrder.class);
        orderMapper.insertSysOrder(order);
    }

    @KafkaListener(topics = "insertOrderItem",groupId ="g2" )
    public void insertOrderItem(ConsumerRecord<?, String> consumerRecord) {
        String value = consumerRecord.value();
        SysOrderItem orderItem = JSON.parseObject(value,SysOrderItem.class);
        orderItemMapper.insertSysOrderItem(orderItem);
    }

    @KafkaListener(topics = "emailUsers",groupId ="g3" )
    public void emailUsers(ConsumerRecord<?, String> consumerRecord) {
        String productStr = consumerRecord.value();
        SysProduct sysProduct = JSON.parseObject(productStr,SysProduct.class);
        String subscribeKey = "subscribe:"+sysProduct.getId();

        Set<String> userSet = redisUtil.smembers(subscribeKey);
        for (String id : userSet){
            SysUser sysUser = sysUserMapper.selectUserById(Long.parseLong(id));
            emailService.sendAttachmentsMail(sysUser.getEmail(),"商品上架提醒","尊贵的LuckyMall商城用户，您订阅的商品"+
                    sysProduct.getProductName()+"上架啦", Constant.IMG_FILE_PATH+sysProduct.getProductPhoto());
        }
        //通知结束后删除该key
        redisUtil.del(subscribeKey);
    }
}
