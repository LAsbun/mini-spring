package com.demo.spring.framework.context;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.Aware;

/**
 * @author shengweisong
 * @date 2021/12/11
 **/
public interface ApplicationContextAware extends Aware {

    /**
     * 感知ApplicationContext
     */
    void setApplicationContext(ApplicationContext context) throws BeansException;
}