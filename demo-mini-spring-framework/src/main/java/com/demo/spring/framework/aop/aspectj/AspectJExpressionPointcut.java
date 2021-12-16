package com.demo.spring.framework.aop.aspectj;

import com.demo.spring.framework.aop.ClassFilter;
import com.demo.spring.framework.aop.MethodMatcher;
import com.demo.spring.framework.aop.Pointcut;
import com.google.common.collect.Sets;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author shengweisong
 * @date 2021/12/15
 **/
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    // 支持的表达式枚举
    private static final Set<PointcutPrimitive> SUPPORT_PORMITIVES = Sets.newConcurrentHashSet();

    static {
        SUPPORT_PORMITIVES.add(PointcutPrimitive.EXECUTION);
    }


    private final PointcutExpression pointcutExpression;


    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORT_PORMITIVES, this.getClass().getClassLoader());
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }


    @Override
    public ClassFilter getClassFileter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }
}