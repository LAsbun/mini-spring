package com.demo.spring.framework.beans.factory.support;

import java.util.HashMap;
import java.util.Map;

/**
 * FactoryBean注册相关抽象接口
 * <p>
 * 这里提供的是FactoryBean存储以及获取的方式. 即提供获取FactoryBean自身.
 *
 * @author shengweisong
 * @date 2021-12-06 8:39 PM
 **/
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /**
     * 这里面存放的是使用FactoryBean创建的实例即beanName:FactoryBean#getObject()
     */
    protected final Map<String, Object> factoryBeanObjectCache = new HashMap<>(16);

}