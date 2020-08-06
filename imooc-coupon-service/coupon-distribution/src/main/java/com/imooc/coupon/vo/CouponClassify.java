package com.imooc.coupon.vo;

import com.imooc.coupon.constans.PeriodType;
import com.imooc.coupon.constant.CouponStatus;
import com.imooc.coupon.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>根据优惠卷的状态对优惠卷进行分类</h1>
 * @author : LuTong.Zhao
 * @date : 22:14 2020/8/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponClassify {
    /**
     * 可用
     */
    private List<Coupon> usable;
    /**
     * 已用
     */
    private List<Coupon> used;
    /**
     * 过期
     */
    private List<Coupon> expired;

    /**
     * <h2>对当前优惠券进行分类</h2>
     * @param coupons
     * @return
     */
    public static CouponClassify classify(List<Coupon> coupons){

        List<Coupon> usable = new ArrayList<>(coupons.size());
        List<Coupon> used = new ArrayList<>(coupons.size());
        List<Coupon> expired = new ArrayList<>(coupons.size());

        coupons.forEach(c ->{
            //判断优惠券是否过期
            boolean isTimeExpire;
            long currentTime = new Date().getTime();
            if (c.getTemplateSDK().getRule().getExpiration().getPeriod().equals(PeriodType.REGULAR.getCode())){
                isTimeExpire = c.getTemplateSDK().getRule().getExpiration().getDeadLine() <= currentTime;
            }else{
                isTimeExpire = DateUtils.addDays(c.getAssignTime(),c.getTemplateSDK().getRule().getExpiration().getGap()).getTime() <= currentTime;
            }

            if (c.getStatus() == CouponStatus.USED){
                used.add(c);
            }else if (c.getStatus() == CouponStatus.EXPIRED || isTimeExpire){
                expired.add(c);
            }else{
                usable.add(c);
            }
        });
        return new CouponClassify(usable,used,expired);
    }

}
