package com.demo.spring.framework.context;

/**
 * 应用事件pulisher
 *
 * @author shengweisong
 * @date 2021/12/10
 **/
public interface ApplicationEventPublisher {
    //  后续开发
    void pulishEvent(ApplicationEvent event);
}