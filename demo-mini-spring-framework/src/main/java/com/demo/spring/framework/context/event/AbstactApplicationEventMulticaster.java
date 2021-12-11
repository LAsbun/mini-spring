package com.demo.spring.framework.context.event;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.BeanFactory;
import com.demo.spring.framework.beans.factory.BeanFactoryAware;
import com.demo.spring.framework.context.ApplicationEvent;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author shengweisong
 * @date 2021/12/11
 **/
public abstract class AbstactApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    public Set<ApplicationListener<ApplicationEvent>> applicationListenerSet = Sets.newHashSet();
    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> applicationListener) {
        applicationListenerSet.add((ApplicationListener<ApplicationEvent>) applicationListener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> applicationListener) {
        applicationListenerSet.remove(applicationListener);
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}