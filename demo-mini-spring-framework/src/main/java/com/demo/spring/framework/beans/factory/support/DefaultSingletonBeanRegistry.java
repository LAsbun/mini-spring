package com.demo.spring.framework.beans.factory.support;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认
 *
 * @author shengweisong
 * @date 2021-12-06 7:19 PM
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * bean 一级缓存
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /**
     * bean 二级缓存 for .AOP.
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    /**
     * 三级缓存。FactoryBean类
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);


    @Override
    public void registerSingleton(String beanName, Object singletonObject) {

        synchronized (this.singletonObjects) {
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new BeansException("no double register same beanName bean [" + beanName
                        + "]");
            }
            addSingleton(beanName, singletonObject);
        }
    }

    private void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            singletonObjects.put(beanName, singletonObject);
            // 从二三级缓存中把对应的单例移除掉.
            earlySingletonObjects.remove(beanName);
            singletonFactories.remove(beanName);
        }
    }


    @Override
    public Object getSingleton(String beanName) {
        // 从一级缓存获取
        Object bean = this.singletonObjects.get(beanName);
        if (bean == null) {
            synchronized (this.singletonObjects) {
                // 二级获取
                bean = this.earlySingletonObjects.get(beanName);
                if (bean == null) {
                    ObjectFactory<?> objectFactory = this.singletonFactories.get(beanName);
                    if (objectFactory != null) {
                        bean = objectFactory.getObject();
                        // 放进二级缓存
                        earlySingletonObjects.put(beanName, bean);
                        // 删除三级缓存中的数据
                        singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return bean;
    }
}