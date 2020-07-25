package com.imooc.coupon.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 产品线枚举
 * @author : LuTong.Zhao
 * @date : 22:39 2020/7/25
 */
@Getter
@AllArgsConstructor
public enum ProductLine {

    DAMAO("大猫",1),
    DABAO("大宝",2);

    /**
     * 产品线描述(分类)
     **/
    private String description;
    /**
     * 产品线编码
     **/
    private Integer code;

    public static ProductLine of(Integer code){

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + "not exists!"));
    }

}
