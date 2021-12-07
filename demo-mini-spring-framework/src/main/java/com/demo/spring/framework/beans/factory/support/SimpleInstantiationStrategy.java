package com.demo.spring.framework.beans.factory.support;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author shengweisong
 * @desc 简单实例化策略实现
 * @date 2021-12-07 5:52 PM
 **/
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition) {
        try {
            Constructor declaredConstructor = beanDefinition.getBeanClass().getDeclaredConstructor();
            return declaredConstructor.newInstance();
        } catch (Exception e) {
            throw new BeansException("Fail to instantiate [" + beanDefinition.getBeanClass().getName() + "]", e);
        }
    }
}