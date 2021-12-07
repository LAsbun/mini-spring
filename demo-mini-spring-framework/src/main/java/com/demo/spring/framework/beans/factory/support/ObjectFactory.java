package com.demo.spring.framework.beans.factory.support;

import com.demo.spring.framework.beans.exception.BeansException;

/**
 * 普通的bean工厂接口。被委托的接口.
 *
 * @author shengweisong
 * @date 2021-12-06 7:59 PM
 **/
public interface ObjectFactory<T> {

    /**
     * 获取对象
     */
    T getObject() throws BeansException;
}