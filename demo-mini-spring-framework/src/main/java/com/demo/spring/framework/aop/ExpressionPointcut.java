package com.demo.spring.framework.aop;

/**
 * 表达式切面接口
 *
 * @author shengweisong
 * @date 2021/12/15
 **/
public interface ExpressionPointcut extends Pointcut {

    /**
     * 获取切面的表达式规则
     */
    String getExpression();
}