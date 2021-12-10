package com.demo.spring.framework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import com.demo.spring.framework.beans.PropertyValues;
import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.BeanFactory;
import com.demo.spring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.demo.spring.framework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.lang.reflect.Field;

/**
 * @author shengweisong
 * @date 2021/12/10
 **/
public class AutowireAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    private ConfigurableListableBeanFactory beanFactory;


    public AutowireAnnotationBeanPostProcessor(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) {
        return false;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) {
        Class<?> clazz = bean.getClass();
        // todo setFieldValue ${}
        // 处理Autowire
        for (Field field : clazz.getDeclaredFields()) {
            Autowire autowire = field.getAnnotation(Autowire.class);
            if (autowire != null) {
                Class<?> type = field.getType();
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                Object dependentBean;
                if (qualifier != null) {
                    String dependentBeanName = qualifier.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, type);

                } else {
                    dependentBean = beanFactory.getBean(type);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);

            }

        }

        return pvs;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) {
        return bean;
    }
}