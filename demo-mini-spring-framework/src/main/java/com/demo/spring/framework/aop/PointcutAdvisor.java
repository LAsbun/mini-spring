package com.demo.spring.framework.aop;

/**
 * Advice对类增强。pointcut指定那些需要增强
 *
 * @author shengweisong
 * @date 2021/12/15
 **/
public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();

}