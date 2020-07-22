package com.imooc.coupon.filter;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * @author : LuTong.Zhao
 * @date : 18:18 2020/7/22
 */
public abstract class AbstractPostZuulFilter extends AbstractZuulFilter{
    /**
     * Filter - POST 阶段的封装
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

}
