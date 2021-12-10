package com.demo.spring.framework.beans.factory.support;

import com.demo.spring.framework.core.io.DefaultResourceLoader;
import com.demo.spring.framework.core.io.Resource;
import com.demo.spring.framework.core.io.ResourceLoader;

/**
 * @author shengweisong
 * @date 2021/12/9
 **/
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private BeanDefinitionRegistery beanDefinitionRegistery;

    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistery registery) {
        this(registery, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistery registery, ResourceLoader resourceLoader) {
        this.beanDefinitionRegistery = registery;
        this.resourceLoader = resourceLoader;
    }


    @Override
    public BeanDefinitionRegistery getRegister() {
        return beanDefinitionRegistery;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }


    @Override
    public void loadBeanDefinitions(String[] locations) {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }

    }
}