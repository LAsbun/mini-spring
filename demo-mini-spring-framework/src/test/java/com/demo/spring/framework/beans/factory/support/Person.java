package com.demo.spring.framework.beans.factory.support;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.DisposableBean;
import com.demo.spring.framework.beans.factory.InitializingBean;
import com.demo.spring.framework.beans.factory.annotation.Autowire;
import com.demo.spring.framework.stereotype.Component;
import lombok.Data;

/**
 * @author shengweisong
 * @date 2021/12/10
 **/
@Data
//@Component
public class Person implements InitializingBean, DisposableBean {

    private String name;

    private int age;

//    @Autowire
    private Car car;

    @Override
    public void destroy() throws Exception {
        System.out.println("person destroy");
    }

    @Override
    public void afterPropertiesSet() throws BeansException {
        System.out.println("init person bean");
    }
}