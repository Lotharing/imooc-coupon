package com.imooc.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <h1>优惠卷kafka消息对象定义</h1>
 * @author : LuTong.Zhao
 * @date : 19:21 2020/8/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponKafkaMessage {
    /**
     * 优惠卷状态
     */
    private Integer status;
    /**
     * 要处理的优惠卷Id
     */
    private List<Integer> ids;

}
