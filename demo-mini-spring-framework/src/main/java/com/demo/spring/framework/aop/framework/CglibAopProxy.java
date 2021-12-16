package com.demo.spring.framework.aop.framework;

import com.demo.spring.framework.aop.framework.autoproxy.AdvisedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author shengweisong
 * @date 2021/12/15
 **/
public class CglibAopProxy implements AopProxy {

    private AdvisedSupport advisedSupport;

    public CglibAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advisedSupport.getTargetSource().getTarget().getClass());
        enhancer.setInterfaces(advisedSupport.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedIntercepter(advisedSupport));
        return enhancer.create();
    }


    // 实际对method代理
    private static class DynamicAdvisedIntercepter implements MethodInterceptor {
        private AdvisedSupport advisedSupport;

        public DynamicAdvisedIntercepter(AdvisedSupport advisedSupport) {
            this.advisedSupport = advisedSupport;
        }


        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            CglibMethodInvocation cglibMethodInvocation = new CglibMethodInvocation(advisedSupport.getTargetSource().getTarget(), method, objects, methodProxy);
            if (advisedSupport.getMethodMatcher().matches(method, advisedSupport.getTargetSource().getTarget().getClass())) {
                return advisedSupport.getMethodInterceptor().invoke(cglibMethodInvocation);
            }
            return cglibMethodInvocation.proceed();
        }


    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] objects, MethodProxy methodProxy) {
            super(target, method, objects);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return methodProxy.invoke(getThis(), getArguments());
        }
    }


}