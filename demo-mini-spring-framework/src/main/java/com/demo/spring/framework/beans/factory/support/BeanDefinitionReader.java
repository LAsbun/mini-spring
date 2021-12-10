package com.demo.spring.framework.beans.factory.support;

import com.demo.spring.framework.core.io.Resource;
import com.demo.spring.framework.core.io.ResourceLoader;

/**
 * 读取class为BeanDefinition
 *
 * @author shengweisong
 * @date 2021/12/9
 **/
public interface BeanDefinitionReader {

    /**
     * 读取到内容，获取注册器。
     */
    BeanDefinitionRegistery getRegister();

    /**
     * 获取资源加载器
     *
     * @return
     */
    ResourceLoader getResourceLoader();

    /**
     * 加载BeanDefinition
     */
    void loadBeanDefinitions(String location);

    /**
     * 加载BeanDefinition
     */
    void loadBeanDefinitions(Resource resource);

    /**
     * 加载BeanDefinition
     */
    void loadBeanDefinitions(String[] locations);


}