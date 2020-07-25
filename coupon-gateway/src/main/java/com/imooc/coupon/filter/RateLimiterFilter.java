package com.imooc.coupon.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.imooc.coupon.constans.SystemEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 限流过滤器： 某个IP在时间断内的请求次数限制  , 系统的请求的时间限制等
 * @author : LuTong.Zhao
 * @date : 18:35 2020/7/22
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class RateLimiterFilter extends AbstractPreZuulFilter{
    /**创建限流器,令牌桶规则：每秒可以获取2个令牌**/
    RateLimiter rateLimiter = RateLimiter.create(2.0);

    /**
     * 自定义过滤器去完成
     * @return
     */
    @Override
    protected Object cRun() {
        HttpServletRequest request = context.getRequest();
        if (rateLimiter.tryAcquire()){
            log.info("get rate token success");
            return success();
        }else{
            log.error("rate limit:{}",request.getRequestURI());
            return fail(SystemEnum.RATE_LIMIT.getCode(),"error: rate limit");
        }
    }

    @Override
    public int filterOrder() {
        return 2;
    }
}
