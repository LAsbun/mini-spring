package com.demo.spring.framework.context;

import java.util.EventObject;

/**
 * @author shengweisong
 * @date 2021/12/10
 **/
public abstract class ApplicationEvent extends EventObject {
    /**
     *
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}