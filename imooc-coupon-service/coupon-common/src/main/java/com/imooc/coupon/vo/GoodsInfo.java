package com.imooc.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品信息
 * @author : LuTong.Zhao
 * @date : 17:17 2020/8/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfo {
    //类型
    private Integer type;
    //价格
    private Double price;
    //数量
    private Integer count;

}
