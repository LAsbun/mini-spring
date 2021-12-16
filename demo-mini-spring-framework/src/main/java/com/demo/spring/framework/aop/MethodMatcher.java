package com.demo.spring.framework.aop;

import java.lang.reflect.Method;

/**
 * @author shengweisong
 * @date 2021/12/15
 **/
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);
}