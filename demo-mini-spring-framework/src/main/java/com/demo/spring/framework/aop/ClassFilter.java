package com.demo.spring.framework.aop;

/**
 * 对需要进行切面的类代理
 *
 * @author shengweisong
 * @date 2021/12/15
 **/
public interface ClassFilter {

    /**
     * 是否对改类进行切面
     *
     * @param clazz
     * @return
     */
    boolean matches(Class<?> clazz);
}