package com.imooc.coupon.service;

import com.imooc.coupon.entity.CouponTemplate;

/**
 * 异步服务接口定义
 * @author : LuTong.Zhao
 * @date : 13:45 2020/7/26
 */
public interface IAsyncService {
    /**
     * <h2>根据模板异步的创建优惠券码</h2>
     * @param template {@link CouponTemplate} 优惠券模板实体
     * */
    void asyncConstructCouponByTemplate(CouponTemplate template);

}
