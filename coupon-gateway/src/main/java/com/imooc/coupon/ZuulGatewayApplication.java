package com.imooc.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关应用启动入口
 * @author : LuTong.Zhao
 * @date : 20:26 2020/7/21
 * @EnableZuulProxy 标识当前的应用是ZuulServer
 * @SpringCloudApplication 组合应用注解  源码：{@SpringBootApplication, @EnableDiscoveryClient 服务发现,@EnableCircuitBreaker 熔断器}
 */
@EnableZuulProxy
@SpringCloudApplication
public class ZuulGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class,args);
    }
}
