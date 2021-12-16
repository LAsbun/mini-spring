package com.demo.spring.framework.aop;

/**
 * 切点封装
 *
 * @author shengweisong
 * @date 2021/12/15
 **/
public interface Pointcut {

    /**
     * 获取需要切面的类
     *
     * @return
     */
    ClassFilter getClassFileter();

    /**
     * 获取切点对应的函数
     * @return
     */
    MethodMatcher getMethodMatcher();

}