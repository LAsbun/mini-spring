package com.demo.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author shengweisong
 * 代理类
 * @date 2021-11-26 4:51 PM
 **/
public class ProxyDynamic implements InvocationHandler {
    private Object bean;
    private Method before;
    private Method after;
    private Object target;
    private String targetMethod;

    public Object createProxy(Object bean, Method before, Method after, Object target, String targetMethod) {
        this.bean = bean;
        this.before = before;
        this.after = after;
        this.target = target;
        this.targetMethod = targetMethod;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.getName().equals(targetMethod)) {
            return method.invoke(this.target, args);
        }

        Object result;
        if (before != null) {
            before.invoke(bean);
        }
        result = method.invoke(target);

        if (after != null) {
            after.invoke(bean);
        }

        return result;
    }
}