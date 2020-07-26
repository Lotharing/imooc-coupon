package com.imooc.coupon.service.impl;

import com.imooc.coupon.dao.CouponTemplateDao;
import com.imooc.coupon.entity.CouponTemplate;
import com.imooc.coupon.exception.CouponException;
import com.imooc.coupon.service.IAsyncService;
import com.imooc.coupon.service.IBuildTemplateService;
import com.imooc.coupon.vo.TemplateRequest;
import com.imooc.coupon.vo.TemplateRule;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : LuTong.Zhao
 * @date : 19:48 2020/7/26
 */
@Slf4j
@Service
public class BuildTemplateService implements IBuildTemplateService {
    /**
     * 异步优惠卷码服务
     */
    @Autowired
    private IAsyncService asyncService;
    /**
     * CouponTemplateDao
     */
    @Autowired
    private CouponTemplateDao templateDao;
    /**
     * <h2>创建优惠券模板</h2>
     *
     * @param request {@link TemplateRequest} 模板信息请求对象
     * @return {@link CouponTemplate} 优惠券模板实体
     */
    @Override
    public CouponTemplate buildTemplate(TemplateRequest request) throws CouponException {
        //参数合法性校验
        if (!request.validate()){
            throw new CouponException("BuildTemplate Param Is Not Valid!");
        }
        //判断同名优惠卷模板是否存在
        if (null != templateDao.findByName(request.getName())){
            throw new CouponException("Exist Same Name Template!");
        }
        //构造CouponTemplate 并保存数据库中
        CouponTemplate template = requestToCouponTemplate(request);
        template = templateDao.save(template);
        // 根据优惠券模板异步生成优惠卷码
        asyncService.asyncConstructCouponByTemplate(template);
        return template;
    }

    /**
     * TemplateRequest 转换为 CouponTemplate 实体
     * @param request
     * @return
     */
    private CouponTemplate requestToCouponTemplate(TemplateRequest request){
        return new CouponTemplate(request.getName(),request.getLogo(),request.getDesc(),request.getCategory(),
                request.getProductLine(),request.getCount(),request.getUserId(),request.getTarget(),request.getRule());
    }

}
