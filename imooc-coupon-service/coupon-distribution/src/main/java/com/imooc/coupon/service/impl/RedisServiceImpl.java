package com.imooc.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.coupon.constans.Constant;
import com.imooc.coupon.constant.CouponStatus;
import com.imooc.coupon.entity.Coupon;
import com.imooc.coupon.exception.CouponException;
import com.imooc.coupon.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>相关的操作服务接口实现</h1>
 * @author : LuTong.Zhao
 * @date : 17:24 2020/8/2
 */
@Slf4j
@Service
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * <h2>根据 userId 和状态找到缓存的优惠券列表数据</h2>
     *
     * @param userId 用户 id
     * @param status 优惠券状态 {@link CouponStatus}
     * @return {@link Coupon}s, 注意, 可能会返回 null, 代表从没有过记录
     */
    @Override
    public List<Coupon> getCachedCoupons(Long userId, Integer status) {
        log.info("Get Coupons From Cache: {}, {}",userId,status);
        String redisKey = status2RedisKey(status,userId);

        List<String> couponStrs = redisTemplate.opsForHash().values(redisKey).stream().map(o -> Objects.toString(o,null)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(couponStrs)){
            saveEmptyCouponListToCache(userId, Collections.singletonList(status));
            return Collections.emptyList();
        }
        return couponStrs.stream().map(cs ->JSON.parseObject(cs,Coupon.class)).collect(Collectors.toList());
    }

    /**
     * <h2>保存空的优惠券列表到缓存中</h2>
     * 目的： 避免缓存穿透
     * @param userId 用户 id
     * @param status 优惠券状态列表
     */
    @Override
    @SuppressWarnings("all")
    public void saveEmptyCouponListToCache(Long userId, List<Integer> status) {
        log.info("Save Empty List To Cache For User: {}, Status: {}",userId, JSON.toJSONString(status));
        //key coupon_id  value 序列化的额Coupon
        Map<String,String> invalidCouponMap = new HashMap<>();
        invalidCouponMap.put("-1",JSON.toJSONString(Coupon.invalidCoupon()));

        //使用redis SessionCallback 把数据命令放入Redis 的 pipeline
        SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {

                status.forEach(s -> {

                    String redisKey = status2RedisKey(s,userId);

                    redisOperations.opsForHash().putAll(redisKey,invalidCouponMap);

                });
                return null;
            }
        };
        log.info("Pipeline Exe Result: {}",JSON.toJSONString(redisTemplate.executePipelined(sessionCallback)));
    }

    /**
     * <h2>尝试从 Cache 中获取一个优惠券码</h2>
     *
     * @param templateId 优惠券模板主键
     * @return 优惠券码
     */
    @Override
    public String tryToAcquireCouponCodeFromCache(Integer templateId) {
        String redisKey = String.format("%s%s",Constant.RedisPrefix.COUPON_TEMPLATE,templateId.toString());
        //因为优惠券码不存在顺序关系，左边pop 右边pop 无影响
        String couponCode = redisTemplate.opsForList().leftPop(redisKey);
        log.info(" Acquire Coupon Code: {} , {}, {}",templateId,redisKey,couponCode);
        return couponCode;
    }

    /**
     * <h2>将优惠券保存到 Cache 中</h2>
     *
     * @param userId  用户 id
     * @param coupons {@link Coupon}s
     * @param status  优惠券状态
     * @return 保存成功的个数
     */
    @Override
    public Integer addCouponToCache(Long userId, List<Coupon> coupons, Integer status) throws CouponException {
        log.info("Add Coupon To Cache: {}, {}, {}",userId, JSON.toJSONString(coupons),status);

        Integer result = -1;

        CouponStatus couponStatus = CouponStatus.of(status);

        switch (couponStatus){
            case USABLE:

                break;
            case USED:
                break;
            case EXPIRED:
                break;
        }

        return result;
    }

    /**
     * <h2>根据status获取对应的redis key</h2>
     * @param status
     * @param userId
     * @return
     */
    private String status2RedisKey(Integer status,Long userId){
        String redisKey = null;
        CouponStatus couponStatus = CouponStatus.of(status);

        switch (couponStatus){
            case USABLE:
                redisKey = String.format("%s%s", Constant.RedisPrefix.USER_COUPON_USABLE,userId);
                break;
            case USED:
                redisKey = String.format("%s%s", Constant.RedisPrefix.USER_COUPON_USERD,userId);
                break;
            case EXPIRED:
                redisKey = String.format("%s%s", Constant.RedisPrefix.USER_COUPON_EXPIRED,userId);
                break;
        }
        return redisKey;
    }

    /**
     * <h2>获取一个随机的过期时间</h2>
     * 缓存雪崩: key 在同一时间失效
     * @param min 最小的小时数
     * @param max 最大的小时数
     * @return 返回 [min, max] 之间的随机秒数
     * */
    private Long getRandomExpirationTime(Integer min, Integer max) {

        return RandomUtils.nextLong(
                min * 60 * 60,
                max * 60 * 60
        );
    }

}
