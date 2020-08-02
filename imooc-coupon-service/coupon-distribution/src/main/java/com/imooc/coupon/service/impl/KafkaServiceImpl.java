package com.imooc.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.coupon.constans.Constant;
import com.imooc.coupon.constant.CouponStatus;
import com.imooc.coupon.entity.Coupon;
import com.imooc.coupon.service.IKafkaService;
import com.imooc.coupon.vo.CouponKafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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
                    break;
                case EXPIRED:
                    break;
            }
        }
    }

}
