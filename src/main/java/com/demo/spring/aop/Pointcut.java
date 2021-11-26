package com.demo.spring.aop;

import java.lang.annotation.*;

/**
 * @author shengweisong
 * @date 2021-11-26 4:38 PM
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Pointcut {

    String value() default "";
}