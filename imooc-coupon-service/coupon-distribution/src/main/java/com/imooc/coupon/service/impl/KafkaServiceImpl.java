package com.imooc.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.coupon.constans.Constant;
import com.imooc.coupon.constant.CouponStatus;
import com.imooc.coupon.dao.CouponDao;
import com.imooc.coupon.entity.Coupon;
import com.imooc.coupon.service.IKafkaService;
import com.imooc.coupon.vo.CouponKafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <h1>kafka 相关的服务接口实现</h1>
 * 核心思想： 将Cache中的Coupon的状态变化同步到 DB 中
 * @author : LuTong.Zhao
 * @date : 19:11 2020/8/2
 */
@Slf4j
@Component
public class KafkaServiceImpl implements IKafkaService {

    @Autowired
    private CouponDao couponDao;

    /**
     * <h2>消费优惠券 Kafka 消息</h2>
     *
     * @param record {@link ConsumerRecord}
     */
    @Override
    @KafkaListener(topics = {Constant.TOPIC},groupId = "imooc-coupon-1")
    public void consumeCouponKafkaMessage(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()){
            Object message = kafkaMessage.get();
            //反序列化新消息
            CouponKafkaMessage couponInfo = JSON.parseObject(message.toString(), CouponKafkaMessage.class);
            log.info("Receive CouponKafkaMessage: {}",message.toString());
            //获取要处理成的状态
            CouponStatus status = CouponStatus.of(couponInfo.getStatus());
            switch (status){
                case USABLE:
                    break;
                case USED:
                    processUsedCoupons(couponInfo,status);
                    break;
                case EXPIRED:
                    processExpiredCoupons(couponInfo,status);
                    break;
            }
        }
    }

    /**
     * 封装一层： 可以在用户使用优惠卷时候做一些其他的操作
     * <h2>处理已使用的用户优惠券</h2>
     * */
    private void processUsedCoupons(CouponKafkaMessage kafkaMessage,
                                    CouponStatus status) {
        // TODO 给用户发送短信
        processCouponsByStatus(kafkaMessage, status);
    }

    /**
     * <h2>处理过期的用户优惠券</h2>
     * */
    private void processExpiredCoupons(CouponKafkaMessage kafkaMessage,
                                       CouponStatus status) {
        // TODO 给用户发送推送
        processCouponsByStatus(kafkaMessage, status);
    }
    /**
     * <h2>根据状态处理优惠券信息</h2>
     * @param kafkaMessage
     * @param status
     */
    private void processCouponsByStatus(CouponKafkaMessage kafkaMessage,CouponStatus status){
        List<Coupon> coupons = couponDao.findAllById(kafkaMessage.getIds());
        if (CollectionUtils.isEmpty(coupons) || coupons.size()!=kafkaMessage.getIds().size()){
            log.error("Can Not Find Right Coupon Info: {}",JSON.toJSONString(kafkaMessage));
            return;
        }
        //修改DB的优惠卷状态
        coupons.forEach(c -> c.setStatus(status));
        log.info("CouponKafkaMessage Op Coupon Count: {}",couponDao.saveAll(coupons).size());
    }

}
