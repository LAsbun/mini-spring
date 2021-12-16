package com.demo.spring.framework.aop.aspectj;

import com.demo.spring.framework.aop.Pointcut;
import com.demo.spring.framework.aop.PointcutAdvisor;
import lombok.Setter;
import org.aopalliance.aop.Advice;

/**
 * @author shengweisong
 * @date 2021/12/15
 **/
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    @Setter
    private String expression;

    private AspectJExpressionPointcut pointcut;

    @Setter
    private Advice advice;

    public AspectJExpressionPointcutAdvisor() {
    }

    public AspectJExpressionPointcutAdvisor(String expression) {
        this.expression = expression;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (pointcut == null) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }


}