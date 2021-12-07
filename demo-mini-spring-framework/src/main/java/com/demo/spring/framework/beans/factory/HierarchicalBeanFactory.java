package com.demo.spring.framework.beans.factory;

/**
 * Bean继承接口.
 * <p>
 * 二次继承。实际框架中提供了两个接口。暂时不清楚用来做什么。但是从继承关系上来看，实际是为了结合BeanFactory和SingletonBeanRegistry
 *
 * @author shengweisong
 * @date 2021-12-03 9:07 PM
 **/
public interface HierarchicalBeanFactory extends BeanFactory {
}