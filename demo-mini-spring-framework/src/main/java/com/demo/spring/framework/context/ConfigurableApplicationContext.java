package com.demo.spring.framework.context;

import com.demo.spring.framework.beans.exception.BeansException;

/**
 * 可配置应用上下文
 *
 * @author shengweisong
 * @date 2021/12/10
 **/
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新bean. 应用上下文核心函数
     *
     * @throws BeansException
     */
    void refresh() throws BeansException;

    /**
     * 关闭应用上下文
     */
    void close();

    /**
     * 向虚拟机中注册一个钩子方法，在虚拟机关闭之前执行关闭容器等操作
     */
    void registerShutdownHook();
}