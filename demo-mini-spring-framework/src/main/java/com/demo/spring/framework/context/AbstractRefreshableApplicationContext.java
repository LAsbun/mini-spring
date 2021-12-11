package com.demo.spring.framework.context;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.BeanFactory;
import com.demo.spring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.demo.spring.framework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author shengweisong
 * @date 2021/12/11
 **/
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;


    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    // 加载all beandefinitions
    public abstract void loadDefinitions(DefaultListableBeanFactory beanFactory);


    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    protected void onRefresh() {

    }
}