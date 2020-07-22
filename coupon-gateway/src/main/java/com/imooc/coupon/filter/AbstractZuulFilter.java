package com.imooc.coupon.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 *
 * 通用的抽象过滤器类
 * 过滤器 extends ZuulFilter
 *
 * 源码： {filterType对应zuul生命周期四个阶段pre post route error , filterOrder 过滤器的优先级设定 , shouldFilter boolean 是否执行run true:执行 , run 过滤器的过滤逻辑}
 * 也就是shouldFilter 返回true的是否 run方法才会执行
 * @author : LuTong.Zhao
 * @date : 20:36 2020/7/21
 */
public abstract class AbstractZuulFilter extends ZuulFilter {
    /**
     * 过滤器之间传递消息,每个请求数据在每个请求的ThreadLocal中,扩展currentHashMap,k v 类型也可以存入
     */
    RequestContext context;
    /**
     * 继续往下执行标识
     */
    private final static String NEXT = "next";

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return (Boolean)ctx.getOrDefault(NEXT,true);
    }

    @Override
    public Object run() throws ZuulException {
        context = RequestContext.getCurrentContext();
        return cRun();
    }

    /**
     * 自定义过滤器去完成
     * @return
     */
    protected abstract Object cRun();

    Object fail(int code ,String msg){
        context.set(NEXT,false);
        context.setSendZuulResponse(false);
        context.getResponse().setContentType("text/html;charset=UTF-8");
        context.setResponseStatusCode(code);
        context.setResponseBody(String.format("{\"result\":\"%s!\"}",msg));
        return null;
    }

    Object success(){
        context.set(NEXT,true);
        return  null;
    }

}
