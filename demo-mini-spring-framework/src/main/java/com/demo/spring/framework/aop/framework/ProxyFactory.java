package com.demo.spring.framework.aop.framework;

import com.demo.spring.framework.aop.framework.autoproxy.AdvisedSupport;

/**
 * @author shengweisong
 * @date 2021/12/15
 **/
public class ProxyFactory {
    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }


    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if(advisedSupport.isProxyTargetClass()){
            return new CglibAopProxy(advisedSupport);
        }
        else {
            return new JDKDynamicAopProxy(advisedSupport);
        }
    }
}