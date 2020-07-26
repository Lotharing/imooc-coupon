package com.imooc.coupon.converter;

import com.imooc.coupon.constans.CouponCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 优惠卷分类枚举属性转换器
 * @author : LuTong.Zhao
 * @date : 13:04 2020/7/26
 * AttributeConverter<X,Y> X实体属性类型 Y数据库字段类型
 */
@Converter
public class CouponCategoryConverter implements AttributeConverter<CouponCategory,String> {
    /**
     * Converts the value stored in the entity attribute into the
     * data representation to be stored in the database.
     *
     * @param couponCategory the entity attribute value to be converted
     * @return the converted data to be stored in the database
     * column
     * 实体属性X转换数据库列Y存储数据库 插入和更新
     */
    @Override
    public String convertToDatabaseColumn(CouponCategory couponCategory) {
        return couponCategory.getCode();
    }

    /**
     * Converts the data stored in the database column into the
     * value to be stored in the entity attribute.
     * Note that it is the responsibility of the converter writer to
     * specify the correct <code>dbData</code> type for the corresponding
     * column for use by the JDBC driver: i.e., persistence providers are
     * not expected to do such type conversion.
     *
     * @param s the data from the database column to be
     *               converted
     * @return the converted value to be stored in the entity
     * attribute
     * 数据库列值Y 转换为实体属性 X 查询操作
     */
    @Override
    public CouponCategory convertToEntityAttribute(String s) {
        return CouponCategory.of(s);
    }
}
