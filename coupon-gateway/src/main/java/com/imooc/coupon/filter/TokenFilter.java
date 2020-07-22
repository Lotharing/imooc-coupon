package com.imooc.coupon.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 校验请求中传递的Token的Filter
 * @author : LuTong.Zhao
 * @date : 18:22 2020/7/22
 */
@Slf4j
@Component
public class TokenFilter extends AbstractPreZuulFilter{
    /**
     * 自定义过滤器去完成
     *
     * @return
     */
    @Override
    protected Object cRun() {
        /**
         * cRun 在 run 中 run中已经初始化了context
         */
        HttpServletRequest request = context.getRequest();
        log.info("%s request to %s",request.getMethod(),request.getRequestURL().toString());
        Object token = request.getParameter("token");
        if (null==token){
            log.error("error: token is empty");
            return fail(401,"error: token is empty");
        }
        return success();
    }

    /***
     * 优先级顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }
}
