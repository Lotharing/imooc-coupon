package com.imooc.coupon.service.impl;

import com.google.common.base.Stopwatch;
import com.imooc.coupon.constans.Constant;
import com.imooc.coupon.dao.CouponTemplateDao;
import com.imooc.coupon.entity.CouponTemplate;
import com.imooc.coupon.service.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 异步服务接口实现
 * @author : LuTong.Zhao
 * @date : 15:41 2020/7/26
 */
@Slf4j
@Service
public class AsyncServiceImpl implements IAsyncService {

    @Autowired
    private CouponTemplateDao templateDao;
    /**
     * redis模板类
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * <h2>根据模板异步的创建优惠券码</h2>
     *
     * @param template {@link CouponTemplate} 优惠券模板实体
     */
    @Async("getAsyncExecutor")
    @Override
    @SuppressWarnings("all")
    public void asyncConstructCouponByTemplate(CouponTemplate template) {

        Stopwatch watch = Stopwatch.createStarted();

        Set<String> couponCodes = buildCouponCode(template);
        //imooc_coupon_template_code_1
        String redisKey = String.format("%s%s", Constant.RedisPrefix.COUPON_TEMPLATE,template.getId().toString());

        log.info("Push CouponCode To Redis: {}",redisTemplate.opsForList().rightPushAll(redisKey,couponCodes));
        //优惠卷模板已经生成
        template.setAvailable(true);
        templateDao.save(template);

        watch.stop();
        log.info("Construct Coupon By Template Coust: {}ms",watch.elapsed(TimeUnit.MILLISECONDS));
        //TODO 发送短信 邮件通知运营人员优惠卷模板可用
        log.info("CouponTemplate({}) Is Available!",template.getId());
    }

    /**
     * 构建优惠卷码(18位 ： 产品线(001) + 类型(1) - 日期(190101) - (随机8位数,第一位不为0)1023456780)
     * @param template {@link CouponTemplate}实体类
     * @return Set(String) 与 template.count 相同个数的优惠卷码
     */
    private Set<String> buildCouponCode(CouponTemplate template){

        Stopwatch watch = Stopwatch.createStarted();
        //不重复(可能result != count 因为不重复的特性,一旦又重复的码就会add error)
        Set<String> result = new HashSet<>(template.getCount());

        //前四位
        String prefix4 = template.getProductLine().getCode().toString() + template.getCategory().getCode().toString();
        //时间
        String date = new SimpleDateFormat("yyMMdd").format(template.getCreateTime());

        for(int i = 0 ; i != template.getCount(); ++i){
            result.add(prefix4 + buildCouponCodeSuffix14(date));
        }

        while (result.size() < template.getCount()){
            result.add(prefix4 + buildCouponCodeSuffix14(date));
        }

        assert result.size() == template.getCount();

        watch.stop();
        log.info("Build Coupon Code Cost: {}",watch.elapsed(TimeUnit.MILLISECONDS));

        return result;
    }

    /**
     * 构造优惠券码的后14位
     * @param date 创建优惠券日期
     * @return 14位优惠券码
     */
    private String buildCouponCodeSuffix14(String date){
        char[] bases = new char[]{'1','2','3','4','5','6','7','8','9'};
        //中间六位
        List<Character>  chars = date.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        //洗牌算法 - 随机过程
        Collections.shuffle(chars);
        String mid6 = chars.stream().map(Objects::toString).collect(Collectors.joining());
        //后八位
        String suffix8 = RandomStringUtils.random(1,bases) + RandomStringUtils.randomNumeric(7);
        return mid6 + suffix8;
    }

}
