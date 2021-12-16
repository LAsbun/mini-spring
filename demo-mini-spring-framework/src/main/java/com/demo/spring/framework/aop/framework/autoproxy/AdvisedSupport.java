package com.demo.spring.framework.aop.framework.autoproxy;

import com.demo.spring.framework.aop.MethodMatcher;
import com.demo.spring.framework.aop.TargetSource;
import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author shengweisong
 * @date 2021/12/15
 **/
@Data
public class AdvisedSupport {

    /**
     * 是否使用cglib
     */
    private boolean proxyTargetClass = true;

    private TargetSource targetSource;

    private MethodInterceptor methodInterceptor;

    private MethodMatcher methodMatcher;

}