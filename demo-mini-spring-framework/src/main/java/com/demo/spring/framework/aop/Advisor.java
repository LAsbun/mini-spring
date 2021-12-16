package com.demo.spring.framework.aop;

import org.aopalliance.aop.Advice;

/**
 * 织入点
 *
 * @author shengweisong
 * @date 2021/12/15
 **/
public interface Advisor {

    Advice getAdvice();
}