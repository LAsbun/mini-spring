package com.demo.spring.framework.context;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.config.BeanPostProcessor;

/**
 * @author shengweisong
 * @date 2021/12/11
 **/
public class ApplicationContextAwarePostProcessor implements BeanPostProcessor {
    private ApplicationContext context;

    public ApplicationContextAwarePostProcessor(ApplicationContext context) {

        this.context = context;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(context);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}