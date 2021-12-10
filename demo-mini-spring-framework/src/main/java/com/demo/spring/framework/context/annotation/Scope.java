package com.demo.spring.framework.context.annotation;

import java.lang.annotation.*;

/**
 * @author shengweisong
 * @date 2021/12/10
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Scope {

    String value() default "singleton";
}