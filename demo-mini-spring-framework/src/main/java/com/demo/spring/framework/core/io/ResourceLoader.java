package com.demo.spring.framework.core.io;

/**
 * 资源加载
 *
 * @author shengweisong
 * @date 2021/12/9
 **/
public interface ResourceLoader {

    /**
     * 加载资源
     */
    Resource loadResource(String location);
}