package com.demo.spring.framework.aop.framework.adapter;

import com.demo.spring.framework.aop.MethodBeforeAdvice;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * BeforeAdvice
 *
 * @author shengweisong
 * @date 2021/12/15
 **/
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {


    @Setter
    private MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(){}

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {

        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 执行之前先执行beforeAdvice
        this.advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}