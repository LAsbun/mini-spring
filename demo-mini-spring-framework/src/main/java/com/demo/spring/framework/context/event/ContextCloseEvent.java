package com.demo.spring.framework.context.event;

import com.demo.spring.framework.context.ApplicationEvent;

/**
 * 上下文关闭事件
 *
 * @author shengweisong
 * @date 2021/12/13
 **/
public class ContextCloseEvent extends ApplicationEvent {
    /**
     * @param source
     */
    public ContextCloseEvent(Object source) {
        super(source);
    }
}