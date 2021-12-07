package com.demo.spring.framework.beans.factory;

import com.demo.spring.framework.beans.exception.BeansException;

/**
 * 自动注入顶级接口。提供自动注入的一些基础接口
 *
 * @author shengweisong
 * @date 2021-12-03 8:30 PM
 **/
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 执行BeanPostProcessors的postProcessBeforeInitialization方法
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException;

    /**
     * 执行BeanPostProcessors的postProcessAfterInitialization方法
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException;
}