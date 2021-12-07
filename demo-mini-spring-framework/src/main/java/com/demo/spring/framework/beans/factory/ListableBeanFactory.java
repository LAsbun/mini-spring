package com.demo.spring.framework.beans.factory;

import com.demo.spring.framework.beans.exception.BeansException;

import java.util.Map;

/**
 * 获取指定类型的所有实例
 * 对IOC统一对外二次封装
 *
 * @author shengweisong
 * @date 2021-12-03 4:31 PM
 **/
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 获取指定类型的全部Bean
     *
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeanOfType(Class<T> type) throws BeansException;


    /**
     * 返回定义的所有bean的名称
     *
     * @return
     */
    String[] getBeanDefinitionNames();
}