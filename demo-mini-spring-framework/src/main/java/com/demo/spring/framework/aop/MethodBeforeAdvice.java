package com.demo.spring.framework.aop;

import java.lang.reflect.Method;

/**
 * @author shengweisong
 * @date 2021/12/15
 **/
public interface MethodBeforeAdvice extends BeforeAdvice {

    /**
     * Method执行 TODO convert有点问题
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}