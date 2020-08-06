package com.imooc.coupon.feign;

import com.imooc.coupon.vo.CommonResponse;
import com.imooc.coupon.vo.CouponTemplateSDK;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <h1>优惠卷模板微服务Feign接口定义</h1>
 * @author : LuTong.Zhao
 * @date : 20:45 2020/8/6
 */
@FeignClient(value = "eureka-client-coupon-template")
public interface TemplateClient {
    /**
     *<h2>查找所有可用的优惠券模板</h2>
     */
    @RequestMapping(value = "/coupon-template/template/sdk/all",method = RequestMethod.GET)
    CommonResponse<List<CouponTemplateSDK>> findAllUsableTemplate();

    /**
     * 获取模板ids到CouponTemplateSDK的映射
     * @param ids
     * @return
     */
    @RequestMapping(value = "/coupon-template/template/sdk/infos",method = RequestMethod.GET)
    CommonResponse<Map<Integer, CouponTemplateSDK>> findIds2TemplateSDK(@RequestParam("ids")Collection<Integer> ids);

}
