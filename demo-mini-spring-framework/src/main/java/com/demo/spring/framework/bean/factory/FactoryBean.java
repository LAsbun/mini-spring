package com.demo.spring.framework.bean.factory;

import com.demo.spring.framework.bean.exception.BeansException;

/**
 * FactoryBean
 *
 * @author shengweisong
 * @date 2021-12-06 9:59 PM
 **/
public interface FactoryBean<T> {

    T getObject() throws BeansException;

    boolean isSingleton();


}