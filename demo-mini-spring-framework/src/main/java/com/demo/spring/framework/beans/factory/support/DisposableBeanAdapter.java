package com.demo.spring.framework.beans.factory.support;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.DisposableBean;
import com.demo.spring.framework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @author shengweisong
 * @date 2021-12-08 10:50 AM
 **/
public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;
    private final String beanName;
    private final String destoryMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destoryMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }
        if (StrUtil.isNotEmpty(this.destoryMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destoryMethodName))) {
            Method declaredMethod = ClassUtil.getDeclaredMethod(bean.getClass(), destoryMethodName);
            if (declaredMethod == null) {
                throw new BeansException("Couldn't find destroyName : " + destoryMethodName + "in beanName:" + beanName);
            }
            declaredMethod.invoke(bean);
        }
    }
}