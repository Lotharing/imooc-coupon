package com.imooc.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 结算信息
 *
 * @author : LuTong.Zhao
 * 1.用户ID
 * 2.商品信息
 * 3.优惠卷信息
 * 4.结算结果金额
 * @date : 17:20 2020/8/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettlementInfo {

    private Integer userId;

    private List<GoodsInfo> goodsInfos;

    private List<CouponAndTemplateInfo> couponAndTemplateInfos;

    /**是否使结算生效,既核销**/
    private Boolean employ;

    private Double cost;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CouponAndTemplateInfo{
        /***
         * coupon主键
         */
        private Integer id;
        /**
         * 对应模板对象
         */
        private CouponTemplateSDK templateSDK;
    }
}
