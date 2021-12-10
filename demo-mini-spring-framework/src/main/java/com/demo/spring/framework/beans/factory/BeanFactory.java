package com.demo.spring.framework.beans.factory;

import com.demo.spring.framework.beans.exception.BeansException;

/**
 * Bean工厂顶级类. 最基本的bean操作
 * 对IOC统一对外封装
 *
 * @author shengweisong
 * @date 2021-12-03 3:44 PM
 **/
public interface BeanFactory {

    /**
     * 根据Bean名称获取Bean实例
     *
     * @param name
     * @return
     * @throws BeansException bean不存在时
     */
    Object getBean(String name) throws BeansException;

    /**
     * 获取指定类型Bean
     *
     * @param requiredType
     * @param <T>       Bean类型
     * @return
     * @throws BeansException bean不存在时
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * 获取指定类型Bean
     *
     * @param name
     * @param requiredType
     * @param <T>
     * @return
     * @throws BeansException bean不存在时
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    /*
     * 包含Bean
     */
    boolean containsBean(String name);

}