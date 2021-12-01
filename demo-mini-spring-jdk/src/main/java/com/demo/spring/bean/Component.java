package com.demo.spring.bean;

import java.lang.annotation.*;

/**
 * @author shengweisong
 * @date 2021-11-26 1:12 PM
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
}