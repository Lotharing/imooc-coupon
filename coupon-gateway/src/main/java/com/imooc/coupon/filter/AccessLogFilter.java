package com.imooc.coupon.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求日志过滤器
 * @author : LuTong.Zhao
 * @date : 18:48 2020/7/22
 */
@Slf4j
@Component
public class AccessLogFilter extends AbstractPostZuulFilter{

    /**
     * 自定义过滤器去完成
     *
     * @return
     */
    @Override
    protected Object cRun() {
        HttpServletRequest request = context.getRequest();

        //从 PreRequestFilter 获取请求时间戳
        long startTime = (Long)context.get("startTime");
        String uri = request.getRequestURI();
        long duration = System.currentTimeMillis() - startTime;

        //从网关通过的请求打印日志记录: uri + duration
        log.info("uri: {},duration: {}",uri,duration);
        return success();
    }

    @Override
    public int filterOrder() {
        /**在过滤器执行返回之前 (-1) 执行过滤器**/
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }
}
