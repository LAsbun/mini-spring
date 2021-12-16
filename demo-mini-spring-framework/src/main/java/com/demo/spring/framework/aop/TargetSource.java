package com.demo.spring.framework.aop;

/**
 * @author shengweisong
 * @date 2021/12/15
 **/
public class TargetSource {

    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetClass() {
        return target.getClass().getInterfaces();
    }

    public Object getTarget() {
        return target;
    }
}