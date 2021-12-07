package com.demo.spring.framework.beans.factory;

import com.demo.spring.framework.beans.exception.BeansException;

/**
 * BeanFactoryAware
 *
 * @author shengweisong
 * @date 2021-12-07 8:42 PM
 **/
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}