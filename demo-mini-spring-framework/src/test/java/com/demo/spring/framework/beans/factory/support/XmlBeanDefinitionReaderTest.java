package com.demo.spring.framework.beans.factory.support;

import org.junit.Test;

public class XmlBeanDefinitionReaderTest {

    @Test
    public void testLoadBeanDefinitions() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");
        Person person = (Person) defaultListableBeanFactory.getBean("person");
        System.out.println(person.getCar().getPerson().getName());
        System.out.println(person.getCar().getPerson().getCar().getBrand());
    }
}