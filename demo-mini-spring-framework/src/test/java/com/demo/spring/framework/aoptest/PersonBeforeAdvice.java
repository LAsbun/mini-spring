package com.demo.spring.framework.aoptest;

import com.demo.spring.framework.aop.MethodBeforeAdvice;
import com.demo.spring.framework.beans.factory.support.Person;

import java.lang.reflect.Method;

/**
 * before  person
 *
 * @author shengweisong
 * @date 2021/12/15
 **/
public class PersonBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        if (target instanceof Person) {
            System.out.println("before person");

            System.out.println("person is: " + ((Person) target).getName());

        }

    }
}