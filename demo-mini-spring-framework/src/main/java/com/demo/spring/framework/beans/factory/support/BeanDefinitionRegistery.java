package com.demo.spring.framework.beans.factory.support;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.config.BeanDefinition;

/**
 * BeanDefinition注册顶级类
 *
 * @author shengweisong
 * @date 2021-12-08
 **/
public interface BeanDefinitionRegistery {

    /**
     * 注册BeanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 获取对应BeanDefinition
     *
     * @param beanName
     * @return
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * BeanName是否已经注册BeanDefinition
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取全部已注册的BeanName
     */
    String[] getBedifinitionNames();
}