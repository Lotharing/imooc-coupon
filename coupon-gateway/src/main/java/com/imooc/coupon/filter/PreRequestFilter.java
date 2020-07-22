package com.imooc.coupon.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 在过滤器中存储 客户端发起请求的时间戳
 * @author : LuTong.Zhao
 * @date : 18:45 2020/7/22
 */
@Slf4j
@Component
public class PreRequestFilter extends AbstractPreZuulFilter{

    /**
     * 自定义过滤器去完成
     * @return
     */
    @Override
    protected Object cRun() {
        context.set("startTime",System.currentTimeMillis());
        return success();
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
