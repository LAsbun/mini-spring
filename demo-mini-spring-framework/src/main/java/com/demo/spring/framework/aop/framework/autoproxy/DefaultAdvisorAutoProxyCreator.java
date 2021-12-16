package com.demo.spring.framework.aop.framework.autoproxy;

import com.demo.spring.framework.aop.*;
import com.demo.spring.framework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.demo.spring.framework.aop.framework.ProxyFactory;
import com.demo.spring.framework.beans.PropertyValues;
import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.BeanFactory;
import com.demo.spring.framework.beans.factory.BeanFactoryAware;
import com.demo.spring.framework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.demo.spring.framework.beans.factory.support.DefaultListableBeanFactory;
import com.google.common.collect.Sets;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Map;
import java.util.Set;

/**
 * 接入SpringIOC
 *
 * @author shengweisong
 * @date 2021/12/15
 **/
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor,
        BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    // 存储已经代理的reference
    private Set<Object> earlyProxyReferences = Sets.newHashSet();


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!this.earlyProxyReferences.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    private Object wrapIfNecessary(Object bean, String beanName) {

        // 如果是框架内的接口实现，就忽略。避免死循环
        if (isInFrastuctureClass(bean.getClass())) {
            return bean;
        }

        Map<String, AspectJExpressionPointcutAdvisor> advisorMap = beanFactory.getBeanOfType(AspectJExpressionPointcutAdvisor.class);

        for (AspectJExpressionPointcutAdvisor advisor : advisorMap.values()) {
            ClassFilter classFileter = advisor.getPointcut().getClassFileter();
            if (classFileter.matches(bean.getClass())) {
                AdvisedSupport advisedSupport = new AdvisedSupport();
                advisedSupport.setTargetSource(new TargetSource(bean));
                advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                return new ProxyFactory(advisedSupport).getProxy();


            }
        }


        return bean;
    }

    /**
     * 判断是不是框架自定义的类
     */
    private boolean isInFrastuctureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) {
        return pvs;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) {
        // 添加已换缓存的reference
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }
}