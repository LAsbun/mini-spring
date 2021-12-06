package com.demo.spring.framework.bean.factory.config;

import com.demo.spring.framework.bean.exception.BeansException;

/**
 * @desc 修改实例化Bean之后的扩展点
 * @Date 2021/12/7
 * Created by shengweisong
 */

public interface BeanPostProcessor {

    /**
     * bean初始化之前执行此方法
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * bean初始化之后执行此方法
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
