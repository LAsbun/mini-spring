package com.demo.spring.framework.context.beantest;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.FactoryBean;
import com.demo.spring.framework.beans.factory.support.Person;

/**
 * 自定义FactoryBean
 *
 * @author shengweisong
 * @date 2021/12/11
 **/
public class CustomFactoryBean implements FactoryBean<Person> {
    @Override
    public Person getObject() throws BeansException {
        Person person = new Person();
        person.setAge(11);
        person.setName("xxx");
        return person;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}