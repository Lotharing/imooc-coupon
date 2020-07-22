package com.imooc.coupon.filter;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * 抽象 过滤器类 pre
 * 扩展： 如果要实现pre类型的过滤器，直接继承此类,实现上层的cRun方法就行了
 * @author : LuTong.Zhao
 * @date : 17:55 2020/7/22
 */
public abstract class AbstractPreZuulFilter extends AbstractZuulFilter{

    /**
     * 实现ZuulFilter的FilterType方法指定当前过滤器的处于声明周期的哪个阶段
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
}
