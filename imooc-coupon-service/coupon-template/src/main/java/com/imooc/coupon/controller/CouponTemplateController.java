package com.imooc.coupon.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.coupon.entity.CouponTemplate;
import com.imooc.coupon.exception.CouponException;
import com.imooc.coupon.service.IBuildTemplateService;
import com.imooc.coupon.service.ITemplateBaseService;
import com.imooc.coupon.vo.CouponTemplateSDK;
import com.imooc.coupon.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <h1>优惠卷模板相关的功能控制器</h1>
 * @author : LuTong.Zhao
 * @date : 18:50 2020/7/27
 */
@Slf4j
@RestController
public class CouponTemplateController {
    /**
     * 构建优惠卷模板服务
     */
    @Autowired
    private IBuildTemplateService buildTemplateService;
    /**
     * 优惠卷模板服务
     */
    @Autowired
    private ITemplateBaseService templateBaseService;

    /**
     * <h2>构建优惠卷模板</h2>
     * 127.0.0.1:7001/coupon-template/template/build
     * # 进入网关 -> prefix:imooc -> 通过:coupon-template转发到对应微服务 -> 映射到路由->/template/build  注:网关需要携带Token,过滤器验证
     * 127.0.0.1:9000/imooc/coupon-template/template/build
     * @param request
     * @return
     * @throws CouponException
     */
    @PostMapping("/template/build")
    public CouponTemplate buildTemplate(@RequestBody TemplateRequest request) throws CouponException{
        log.info("Build Template: {}", JSON.toJSONString(request));
        return buildTemplateService.buildTemplate(request);
    }

    /**
     * <h2>构造优惠券模板详情</h2>
     * 127.0.0.1:7001/coupon-template/template/info?id=1
     * 127.0.0.1:9000/imooc/coupon-template/template/info?id=1
     * @param id
     * @return
     * @throws CouponException
     */
    @GetMapping("/template/info")
    public CouponTemplate buildTemplateInfo(@RequestParam("id") Integer id) throws CouponException{
        log.info("Build Template Info For: {}", id);
        return templateBaseService.buildTemplateInfo(id);
    }

    /**
     * <h2>查找所有可用的优惠券模板</h2>
     * 127.0.0.1:7001/coupon-template/template/sdk/all
     * 127.0.0.1:9000/imooc/coupon-template/template/sdk/all
     * @return
     */
    @GetMapping("/template/sdk/all")
    public List<CouponTemplateSDK> findAllUsableTemplate(){
        log.info("Find All Usable Template.");
        return templateBaseService.findAllUsableTemplate();
    }

    /**
     * <h2>获取模板ids 到 CouponTemplateSDK 的映射</h2>
     * 127.0.0.1:7001/coupon-template/template/sdk/infos
     * 127.0.0.1:9000/imooc/coupon-template/template/sdk/infos
     * @return
     */
    @GetMapping("/template/sdk/infos")
    public Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(@RequestParam("ids") Collection<Integer> ids){
        log.info("findIds2TemplateSDK: {}",JSON.toJSONString(ids));
        return templateBaseService.findIds2TemplateSDK(ids);
    }

}
