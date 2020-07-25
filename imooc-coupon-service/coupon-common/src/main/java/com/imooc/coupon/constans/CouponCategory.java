package com.imooc.coupon.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 优惠卷分类枚举
 * @author : LuTong.Zhao
 * @date : 22:31 2020/7/25
 */
@Getter
@AllArgsConstructor
public enum CouponCategory {

    MANJIAN("满减券","001"),
    ZHEKOU("折扣卷","002"),
    LIJIAN("立减券","003");

    /**
     * 优惠卷描述(分类)
     **/
    private String description;
    /**
     * 优惠卷编码
     **/
    private String code;

    public static CouponCategory of(String code){

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + "not exists!"));
    }

}
