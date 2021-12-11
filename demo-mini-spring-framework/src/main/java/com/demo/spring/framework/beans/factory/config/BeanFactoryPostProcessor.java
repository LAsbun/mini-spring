package com.demo.spring.framework.beans.factory.config;

import com.demo.spring.framework.beans.factory.ConfigurableListableBeanFactory;

/**
 * 提供对BeanDefinition可修改的接口
 *
 * @author shengweisong
 * @date 2021/12/10
 **/
public interface BeanFactoryPostProcessor {

    /**
     * 提供对BeanDefinition方式
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);
}