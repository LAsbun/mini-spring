package com.demo.spring.framework.context.event;

import com.demo.spring.framework.context.ApplicationEvent;

/**
 * 当Application加载或刷新完成之后触发
 *
 * @author shengweisong
 * @date 2021/12/11
 **/
public class ContextRefreshedEvent extends ApplicationEvent {
    /**
     * @param source
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}