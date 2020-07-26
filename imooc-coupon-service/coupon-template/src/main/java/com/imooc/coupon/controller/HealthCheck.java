package com.imooc.coupon.controller;

import com.imooc.coupon.exception.CouponException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>健康检查接口</h1>
 * @author : LuTong.Zhao
 * @date : 22:36 2020/7/26
 */
@Slf4j
@RestController
public class HealthCheck {

    /**
     * 服务发现客户端 -  获取其他服务元信息
     */
    @Autowired
    private DiscoveryClient client;
    /**
     * 服务注册接口 - 注册后 提供了获取服务ID的方法 Id可以查询服务的信息
     */
    @Autowired
    private Registration registration;

    /**
     * <h2>健康检查接口</h2>
     * 127.0.0.1:7001/coupon-template/health
     * @return
     */
    @GetMapping("/health")
    public String health(){
        log.debug("view health api");
        return "CouponTemplate Is Ok!";
    }

    /**
     * <h2>异常测试接口 - 检查异常是否好用</h2>
     * 127.0.0.1:7001/coupon-template/exception
     * @return
     * @throws CouponException
     */
    @GetMapping("/exception")
    public String exception() throws CouponException {
        log.debug("view exception api");
        throw new CouponException("CouponTemplate Has Some Problem");
    }

    /**
     * <h2>获取Eureka Server 上微服务元信息</h2>
     * 127.0.0.1:7001/coupon-template/info
     * @return
     */
    @GetMapping("/info")
    public List<Map<String, Object>> info(){
        //大约两分钟才能获取到注册信息 List 可能是多实例部署
        List<ServiceInstance> instances = client.getInstances(registration.getServiceId());
        List<Map<String, Object>> result = new ArrayList<>(instances.size());
        //获取所有实例信息
        instances.forEach(i ->{
            Map<String, Object> info = new HashMap<>();
            info.put("serviceId",i.getServiceId());
            info.put("instanceId",i.getInstanceId());
            info.put("hort",i.getPort());
            info.put("host",i.getHost());
            result.add(info);
        });
        return result;
    }

}
