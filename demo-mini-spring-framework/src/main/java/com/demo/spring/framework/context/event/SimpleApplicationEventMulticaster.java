package com.demo.spring.framework.context.event;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.BeanFactory;
import com.demo.spring.framework.context.ApplicationEvent;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author shengweisong
 * @date 2021/12/11
 **/
public class SimpleApplicationEventMulticaster extends AbstactApplicationEventMulticaster {


    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicaster(ApplicationEvent applicationEvent) {
        for (ApplicationListener<ApplicationEvent> applicationListener : applicationListenerSet) {
            if (surpportEvent(applicationListener, applicationEvent)) {
                applicationListener.onApplicationEvent(applicationEvent);
            }
        }

    }

    /**
     * 判断是否listener感兴趣的时间
     */
    private boolean surpportEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent applicationEvent) {
        Type type = applicationListener.getClass().getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        String typeName = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + typeName);
        }
        return eventClassName.isAssignableFrom(applicationEvent.getClass());
    }
}