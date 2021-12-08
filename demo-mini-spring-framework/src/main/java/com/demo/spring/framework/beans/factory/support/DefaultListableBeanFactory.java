package com.demo.spring.framework.beans.factory.support;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.demo.spring.framework.beans.factory.config.BeanDefinition;

import java.util.Map;

/**
 * 默认IOC 类
 *
 * @author shengweisong
 * @date 2021/12/8
 **/
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory, BeanDefinitionRegistery {



    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return null;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return false;
    }

    @Override
    public String[] getBedifinitionNames() {
        return new String[0];
    }

    @Override
    protected boolean containsBeanDifinition(String beanName) {
        return false;
    }

    @Override
    public <T> Map<String, T> getBeanOfType(Class<T> type) throws BeansException {
        return null;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }
}