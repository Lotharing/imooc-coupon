package com.imooc.coupon.constans;

/**
 * 通用常量定义
 * @author : LuTong.Zhao
 * @date : 15:35 2020/7/26
 */
public class Constant {
    /**
     * kafka消息topic
     */
    public static final String TOPIC = "immoc_user_coupon_op";

    public static class RedisPrefix{
        /**
         * 优惠卷码 key 前缀
         */
        public static final String COUPON_TEMPLATE = "imooc_coupon_template_code_";
        /**
         * 用户当前所有可用优惠卷 key 前缀
         */
        public static final String USER_COUPON_USABLE = "imooc_user_coupon_usable_";
        /**
         * 用户当前所有已使用的优惠卷 key 前缀
         */
        public static final String USER_COUPON_USERD = "imooc_user_coupon_used_";
        /**
         * 用户当前所有已过期的优惠卷 key 前缀
         */
        public static final String USER_COUPON_EXPIRED = "imooc_user_coupon_expired_";

    }
}
