package com.demo.spring.framework.beans.factory.config;

/**
 * Bean引用类
 *
 * @author shengweisong
 * @date 2021-12-07 8:29 PM
 **/
public class BeanReference {
    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}