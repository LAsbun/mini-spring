package com.demo.spring.framework.beans.factory.support;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.config.BeanDefinition;

/**
 * 实例化策略接口类
 *
 * @author shengweisong
 * @date 2021-12-07 5:51 PM
 **/
public interface InstantiationStrategy {

    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}