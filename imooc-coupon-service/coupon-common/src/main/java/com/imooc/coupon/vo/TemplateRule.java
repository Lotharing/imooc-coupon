package com.imooc.coupon.vo;

import com.imooc.coupon.constans.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 优惠卷规则对象定义
 * @author : LuTong.Zhao
 * @date : 23:07 2020/7/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRule {

    /**
     * 优惠卷过期规则
     */
    private Expiration expiration;
    /**
     * 折扣规则
     */
    private Discount discount;
    /**
     * 每个人阵对优惠卷可以领几张限制
     */
    private Integer limitation;
    /**
     * 使用范围 ： 地域 + 商品类型
     */
    private Usage usage;
    /**
     * 权重： 可以和哪些优惠卷叠加使用 - 同一类优惠卷不能叠加使用 list[] , 优惠卷的唯一编码
     */
    private String weight;

    /**
     * 校验功能
     * @return
     */
    public boolean validate(){
        return expiration.validate() && discount.validate() && limitation.intValue()>0 && usage.vaildate() && StringUtils.isNotEmpty(weight);
    }

    /**
     * 如果是SHIFT变动的,那么gap有效记录领取后过期时间 + deadline是模板的有效期也有效
     * 不变动 deadline 作用有效日期
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Expiration{
        /**
         * 有效期规则 ： 对应PeriodType 的 code
         */
        private Integer period;
        /**
         * 有效间隔 只对变动型有效期有效
         */
        private Integer gap;
        /**
         * 优惠卷 模板 失效日期  变动不变动 都有效
         */
        private Long deadLine;

        boolean validate(){
            //简化校验
            return null != PeriodType.of(period) && gap > 0 && deadLine >0;
        }
    }

    /**
     * 折扣,需要与类型配合决定
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Discount{
        /**
         * 额度 满减(20) 折扣(85) 类型判断折扣 20 / 0.85  立减(10)
         */
        private Integer quota;
        /**
         * 满减-类型基准
         */
        private Integer base;

        boolean validate(){
            return quota > 0 && base > 0;
        }
    }

    /**
     * 使用范围
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Usage{
        /**
         * 省份
         */
        private String province;
        /**
         * 城市
         */
        private String city;
        /**
         * 商品类型 list[文娱,生鲜,家具]
         */
        private String goodsType;

        boolean vaildate(){
            return StringUtils.isNotEmpty(province) && StringUtils.isNotEmpty(city) && StringUtils.isNotEmpty(goodsType);
        }
    }

}
