package com.demo.spring.aop;

/**
 * AopDemo
 *
 * @author shengweisong
 * @date 2021-11-26 5:05 PM
 **/
@Aspect
public class AopDemo {

    @Pointcut("com.demo.spring.service.impl.DemoServiceImpl.helloWorld()")
    public void aoppp() {

    }


    @Before("aoppp()")
    public void beforeAoppp() {
        System.out.println("before");
    }

    @After("aoppp()")
    public void afterAoppp() {
        System.out.println("after");
    }

}