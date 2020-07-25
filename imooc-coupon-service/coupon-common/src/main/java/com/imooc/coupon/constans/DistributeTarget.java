package com.imooc.coupon.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 分发目标枚举
 * @author : LuTong.Zhao
 * @date : 22:42 2020/7/25
 */
@Getter
@AllArgsConstructor
public enum DistributeTarget {

    SINGLE("单用户",1),
    MULTI("多用户",2);

    /**
     * 分发目标描述(分类)
     **/
    private String description;
    /**
     * 分发目标编码
     **/
    private Integer code;

    public static DistributeTarget of(Integer code){

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + "not exists!"));
    }

}
