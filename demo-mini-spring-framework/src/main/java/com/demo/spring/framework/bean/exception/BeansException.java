package com.demo.spring.framework.bean.exception;

/**
 * Beans 基础异常类
 *
 * @author shengweisong
 * @date 2021-12-03 3:46 PM
 **/
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        //
        super(msg, cause);
    }
}