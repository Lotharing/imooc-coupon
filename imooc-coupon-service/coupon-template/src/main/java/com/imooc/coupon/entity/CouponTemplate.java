package com.imooc.coupon.entity;

import com.imooc.coupon.constans.CouponCategory;
import com.imooc.coupon.constans.DistributeTarget;
import com.imooc.coupon.constans.ProductLine;
import com.imooc.coupon.converter.CouponCategoryConverter;
import com.imooc.coupon.converter.DistributeTargetConverter;
import com.imooc.coupon.converter.ProductLineConverter;
import com.imooc.coupon.converter.RuleConverter;
import com.imooc.coupon.vo.TemplateRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 优惠卷模板实体定义: 1.基础属性 2。规则属性
 * @author : LuTong.Zhao
 * @date : 23:30 2020/7/25
 * @Entity 数据表实体类
 * @Tables 对应数据库表
 * @EntityListeners JPA 自动赋值 createTime
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon_template")
public class CouponTemplate {
    /**
     * @Id 主键
     * @GeneratedValue 主键生成策略 自增
     * @Column 对应表列id,false 非空
     * @Basic 每个列都有这个隐藏属性,可以加可以不加,说明字段属于数据库表中的列
     * @Transient 不要把列映射数据表中,数据表中不存在,应用:定制额外属性,属性不存在数据库表
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;
    /**
     * 是否可用状态
     */
    @Column(name = "available",nullable = false)
    private Boolean available;
    /**
     * 是否过期
     */
    @Column(name = "expired",nullable = false)
    private Boolean expired;
    /**
     * 优惠卷名称
     */
    @Column(name = "name",nullable = false)
    private String name;
    /**
     * 优惠卷logo
     */
    @Column(name = "logo",nullable = false)
    private String logo;
    /**
     * 优惠卷描述
     */
    @Column(name = "intro",nullable = false)
    private String desc;
    /**
     * 优惠卷分类
     */
    @Column(name = "category",nullable = false)
    @Convert(converter = CouponCategoryConverter.class)
    private CouponCategory category;
    /**
     * 产品线
     */
    @Column(name = "product_line",nullable = false)
    @Convert(converter = ProductLineConverter.class)
    private ProductLine productLine;
    /**
     * 总数
     */
    @Column(name = "coupon_count",nullable = false)
    private Integer count;
    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time",nullable = false)
    private Date createTime;
    /**
     * 创建用户
     */
    @Column(name = "user_id",nullable = false)
    private Long userId;
    /**
     * 优惠卷模板编码
     */
    @Column(name = "template_key",nullable = false)
    private String key;
    /**
     * 目标用户
     */
    @Column(name = "target",nullable = false)
    @Convert(converter = DistributeTargetConverter.class)
    private DistributeTarget target;
    /**
     * 优惠卷规则
     */
    @Column(name = "rule",nullable = false)
    @Convert(converter = RuleConverter.class)
    private TemplateRule rule;

    /**
     * 自定义构造函数
     * @param name
     * @param logo
     * @param desc
     * @param category
     * @param productLine
     * @param count
     * @param userId
     * @param target
     * @param rule
     */
    public CouponTemplate(String name, String logo, String desc, String category,
                          Integer productLine, Integer count, Long userId,
                          Integer target, TemplateRule rule) {

        this.available = false;
        this.expired = false;
        this.name = name;
        this.logo = logo;
        this.desc = desc;
        this.category = CouponCategory.of(category);
        this.productLine = ProductLine.of(productLine);
        this.count = count;
        this.userId = userId;
        // 优惠券模板唯一编码 = 4(产品线和类型) + 8(日期: 20190101) + id(扩充为4位)
        this.key = productLine.toString() + category +
                new SimpleDateFormat("yyyyMMdd").format(new Date());
        this.target = DistributeTarget.of(target);
        this.rule = rule;
    }

}
