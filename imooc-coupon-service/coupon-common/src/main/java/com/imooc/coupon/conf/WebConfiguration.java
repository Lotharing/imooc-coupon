package com.imooc.coupon.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 定制 HTTP 消息转换器
 * Java 的对象转换 HTTP 输出流 | SpringBoot底层 jackson库实体转换json格式 | 多个转换器会根据消息/内容类型选择最合适的
 * MappingJackson2HttpMessageConverter：  javaBean -> application/json
 * @author : LuTong.Zhao
 * @date : 19:09 2020/7/22
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        /**添加自定义转换器 REST/JSON格式返回**/
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
