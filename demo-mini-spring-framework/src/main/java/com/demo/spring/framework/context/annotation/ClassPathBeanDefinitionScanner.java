package com.demo.spring.framework.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.demo.spring.framework.beans.factory.annotation.AutowireAnnotationBeanPostProcessor;
import com.demo.spring.framework.beans.factory.config.BeanDefinition;
import com.demo.spring.framework.beans.factory.support.BeanDefinitionRegistery;
import com.demo.spring.framework.stereotype.Component;

import java.util.Set;

/**
 * 扫描指定package 路径下面的类封装成BeanDefinition
 *
 * @author shengweisong
 * @date 2021/12/10
 **/
public class ClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScannerComponentProvider {

    private final static String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME = "com.demo.spring.framework.beans.factory.annotation.internalAutowiredAnnotationProcessor";

    private BeanDefinitionRegistery register;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistery register) {

        this.register = register;
    }

    public void doScan(String[] basePackages) {

        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidateComponents) {
                String scope = resolveScope(beanDefinition);
                if (StrUtil.isNotEmpty(scope)) {
                    beanDefinition.setScope(scope);
                }
                String beanName = determineBeanName(beanDefinition);
                register.registerBeanDefinition(beanName, beanDefinition);
            }

        }

        // 注册自动注入AutoWire相关的BeanPostProcessor
        register.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, new BeanDefinition(AutowireAnnotationBeanPostProcessor.class));

    }

    /**
     * 找到对应的Scope
     */
    private String resolveScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) {
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 获取Componet注册的类 beanName
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        if (StrUtil.isNotEmpty(component.value())) {
            return component.value();
        }
        return StrUtil.lowerFirst(beanClass.getSimpleName());
    }
}