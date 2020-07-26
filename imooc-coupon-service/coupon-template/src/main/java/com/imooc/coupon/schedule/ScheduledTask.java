package com.imooc.coupon.schedule;

import com.imooc.coupon.dao.CouponTemplateDao;
import com.imooc.coupon.entity.CouponTemplate;
import com.imooc.coupon.vo.TemplateRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>定时清理已过期的优惠卷模板</h1>
 * @author : LuTong.Zhao
 * @date : 20:16 2020/7/26
 */
@Slf4j
@Component
public class ScheduledTask {
    /**
     * CouponTemplate Dao接口
     */
    @Autowired
    private CouponTemplateDao templateDao;

    /**
     * <h2>下线已过期的优惠卷模板</h2>
     */
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void offlineCouponTemplate(){
        log.info("Start To Expire CouponTemplate");
        //当前未过期的优惠卷模板
        List<CouponTemplate> templates = templateDao.findAllByExpired(false);
        if (CollectionUtils.isEmpty(templates)){
            log.info("Done To Expire CouponTemplate");
            return;
        }
        Date cur = new Date();
        List<CouponTemplate> expiredTemplates = new ArrayList<>(templates.size());

        templates.forEach(t ->{
            //根据"过期规则"判断是否已过期 过期修改标记->添加到待处理过期List中->存储数据库  && 启到过滤过期的功能 解决定时的弊端
            TemplateRule rule = t.getRule();
            if (rule.getExpiration().getDeadLine() < cur.getTime()){
                t.setExpired(true);
                expiredTemplates.add(t);
            }
        });

        if (CollectionUtils.isNotEmpty(expiredTemplates)){
            log.info("Expired CouponTemplate Num: {}",templateDao.saveAll(expiredTemplates));
        }
        log.info("Done To Expire CouponTemplate");
    }

}
