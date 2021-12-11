package com.demo.spring.framework.context;

import com.demo.spring.framework.beans.factory.HierarchicalBeanFactory;
import com.demo.spring.framework.beans.factory.ListableBeanFactory;
import com.demo.spring.framework.core.io.ResourceLoader;

/**
 * BeanFactory 上层应用接口
 *
 * @author shengweisong
 * @date 2021/12/10
 **/
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}