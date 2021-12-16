package com.demo.spring.framework.aop.framework;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * @author shengweisong
 * @date 2021/12/15
 **/
public class ReflectiveMethodInvocation implements MethodInvocation {


    private final Object target;
    private final Method method;
    private final Object[] arguments;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {

        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target);
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}