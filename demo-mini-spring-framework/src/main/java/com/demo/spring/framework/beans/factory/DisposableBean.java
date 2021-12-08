package com.demo.spring.framework.beans.factory;

/**
 * 销毁bean
 *
 * @author shengweisong
 * @date 2021-12-08 10:48 AM
 **/
public interface DisposableBean {

    void destroy() throws Exception;
}