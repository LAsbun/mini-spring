package com.demo.spring.framework.beans.factory;

import com.demo.spring.framework.beans.exception.BeansException;

/**
 * 初始化属性之后调用: 用来做一些额外补充属性设置
 * 比如org.springframework.cache.support.AbstractCacheManager会初始化caches
 *
 * @author shengweisong
 * @date 2021-12-08 10:19 AM
 **/
public interface InitializingBean {

    /**
     *
     */
    void afterPropertiesSet() throws BeansException;
}