package com.demo.spring.framework.bean.factory.support;

import com.demo.spring.framework.bean.exception.BeansException;
import com.demo.spring.framework.bean.factory.AutowireCapableBeanFactory;
import com.demo.spring.framework.bean.factory.BeanDefinition;

/**
 * @desc AutowireBeanFactory抽象impl
 * @Date 2021/12/7
 * Created by shengweisong
 */
// todo

public abstract class AbstractAutowireCapableBenFactory extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        return null;
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        return null;
    }

}
