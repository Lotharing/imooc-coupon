package com.imooc.coupon.service.impl;

import com.imooc.coupon.dao.CouponTemplateDao;
import com.imooc.coupon.entity.CouponTemplate;
import com.imooc.coupon.exception.CouponException;
import com.imooc.coupon.service.ITemplateBaseService;
import com.imooc.coupon.vo.CouponTemplateSDK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <h1>优惠卷模板基础服务接口实现</h1>
 * @author : LuTong.Zhao
 * @date : 20:00 2020/7/26
 */
@Slf4j
@Service
public class TemplateBaseServiceImpl implements ITemplateBaseService {
    /**
     * Template Dao 接口
     */
    @Autowired
    private CouponTemplateDao templateDao;
    /**
     * <h2>根据优惠券模板 id 获取优惠券模板信息</h2>
     *
     * @param id 模板 id
     * @return {@link CouponTemplate} 优惠券模板实体
     */
    @Override
    public CouponTemplate buildTemplateInfo(Integer id) throws CouponException {
        Optional<CouponTemplate> template = templateDao.findById(id);
        if (!template.isPresent()){
            throw new CouponException("Template Is Not Exist: "+id);
        }
        return template.get();
    }

    /**
     * <h2>查找所有可用的优惠券模板</h2>
     *
     * @return {@link CouponTemplateSDK}s
     */
    @Override
    public List<CouponTemplateSDK> findAllUsableTemplate() {
        //可用状态和没有过期 因为定时任务清理所以还要校验
        List<CouponTemplate> templates = templateDao.findAllByAvailableAndExpired(true,false);
        return templates.stream().map(this::template2TemplateSDK).collect(Collectors.toList());
    }

    /**
     * <h2>获取模板 ids 到 CouponTemplateSDK 的映射</h2>
     *
     * @param ids 模板 ids
     * @return Map<key: 模板 id  value: CouponTemplateSDK>
     */
    @Override
    public Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(Collection<Integer> ids) {
        List<CouponTemplate> templates = templateDao.findAllById(ids);
        return templates.stream().map(this::template2TemplateSDK).collect(Collectors.toMap(CouponTemplateSDK::getId, Function.identity()));
    }

    /**
     * <h2>CouponTemplate 转换为 CouponTemplateSDK</h2>
     * @param template
     * @return
     */
    private CouponTemplateSDK template2TemplateSDK(CouponTemplate template){
        return new CouponTemplateSDK(template.getId(),template.getName(),template.getLogo(),template.getDesc(),template.getCategory().getCode(),
                template.getProductLine().getCode(),template.getKey(),template.getTarget().getCode(),template.getRule());
    }

}
