package com.demo.spring.framework.context;

import com.demo.spring.framework.beans.factory.support.DefaultListableBeanFactory;
import com.demo.spring.framework.beans.factory.support.XmlBeanDefinitionReader;

/**
 * @author shengweisong
 * @date 2021/12/11
 **/
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
    @Override
    public void loadDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String[] locations = getLocations();
        if (null != locations) {
            xmlBeanDefinitionReader.loadBeanDefinitions(locations);
        }
    }

    protected abstract String[] getLocations();
}