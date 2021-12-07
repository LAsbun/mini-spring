package com.demo.spring.framework.beans.factory.config;

import com.demo.spring.framework.beans.PropertyValue;
import com.demo.spring.framework.beans.PropertyValues;

/**
 * BeanPostProcessor实例化接口
 *
 * @author shengweisong
 * @date 2021-12-07 5:35 PM
 **/
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 实例化之前执行
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName);

    /**
     * 实例化之后，设置属性之前
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName);

    /**
     * 实例化之后，设置属性之前
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName);
}