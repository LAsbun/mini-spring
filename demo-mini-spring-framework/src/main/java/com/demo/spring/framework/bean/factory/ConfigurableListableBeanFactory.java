package com.demo.spring.framework.bean.factory;

import com.demo.spring.framework.bean.exception.BeansException;

/**
 * 框架接口
 *
 * @author shengweisong
 * @date 2021-12-03 8:49 PM
 **/
public interface ConfigurableListableBeanFactory extends ConfigurableBeanFactory,
        ListableBeanFactory, AutowireCapableBeanFactory {

    /**
     * 获取BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}