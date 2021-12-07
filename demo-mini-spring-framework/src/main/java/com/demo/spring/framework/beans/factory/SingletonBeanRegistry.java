package com.demo.spring.framework.beans.factory;

import com.sun.istack.internal.Nullable;

/**
 * 单例Bean注册顶级基类
 * PS: 单例三级缓存就在这下面来实现.
 *
 * @author shengweisong
 * @date 2021-12-03 4:42 PM
 **/
public interface SingletonBeanRegistry {

    /**
     * 注册beanName以及单例
     * 这里注册的就是真实单例bean
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取单例bean
     */
    @Nullable
    Object getSingleton(String beanName);


}