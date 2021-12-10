package com.demo.spring.framework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.demo.spring.framework.beans.factory.config.BeanDefinition;
import com.demo.spring.framework.stereotype.Component;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

/**
 * 扫描Component注释的类
 *
 * @author shengweisong
 * @date 2021/12/10
 **/
public class ClassPathBeanDefinitionScannerComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        HashSet<BeanDefinition> candidates = Sets.newHashSet();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            candidates.add(beanDefinition);
        }

        return candidates;

    }
}