package com.demo.spring.framework.context.event;

import com.demo.spring.framework.context.ApplicationEvent;

import java.util.EventListener;

/**
 * @author shengweisong
 * @date 2021/12/11
 **/
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    void onApplicationEvent(E event);
}