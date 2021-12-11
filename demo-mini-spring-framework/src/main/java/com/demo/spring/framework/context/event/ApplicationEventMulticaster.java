package com.demo.spring.framework.context.event;

import com.demo.spring.framework.context.ApplicationEvent;

/**
 * 事件传播接口
 *
 * @author shengweisong
 * @date 2021/12/11
 **/
public interface ApplicationEventMulticaster {

    /**
     * 添加监听器
     */
    void addApplicationListener(ApplicationListener<?> applicationListener);

    /**
     * 移除监听器
     */
    void removeApplicationListener(ApplicationListener<?> applicationListener);

    /**
     * 传播事件
     */
    void multicaster(ApplicationEvent applicationEvent);
}