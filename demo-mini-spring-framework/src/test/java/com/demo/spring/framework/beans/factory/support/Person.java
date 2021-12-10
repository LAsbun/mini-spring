package com.demo.spring.framework.beans.factory.support;

import com.demo.spring.framework.beans.factory.annotation.Autowire;
import com.demo.spring.framework.stereotype.Component;
import lombok.Data;

/**
 * @author shengweisong
 * @date 2021/12/10
 **/
@Data
@Component
public class Person {

    private String name;

    private int age;

    @Autowire
    private Car car;

}