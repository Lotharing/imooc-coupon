package com.imooc.coupon.feign;

import com.imooc.coupon.exception.CouponException;
import com.imooc.coupon.vo.CommonResponse;
import com.imooc.coupon.vo.SettlementInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <h1>优惠券结算微服务feign接口定义</h1>
 * @author : LuTong.Zhao
 * @date : 20:54 2020/8/6
 */
@FeignClient(value = "eureka-client-coupon-settlement")
public interface SettlementClient {
    /**
     * 优惠券规则计算
     * @param settlement
     * @return
     * @throws CouponException
     */
    @RequestMapping(value = "/coupon-settlement/settlement/compute",method = RequestMethod.POST)
    CommonResponse<SettlementInfo> computeRule(@RequestBody SettlementInfo settlement) throws CouponException;

}
