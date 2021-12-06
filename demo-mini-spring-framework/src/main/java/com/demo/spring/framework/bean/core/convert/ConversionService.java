package com.demo.spring.framework.bean.core.convert;

/**
 * @desc 类型转换抽象接口
 * @Date 2021/12/7
 * Created by shengweisong
 */

public interface ConversionService {

    /**
     * souce是否可以转换为targetType
     */
    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    /**
     * source 转换为TargetType类型的数据
     */
    <T> T convert(Object source, Class<T> targetType);
}
