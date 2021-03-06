package com.demo.spring.starter;

import com.demo.spring.bean.BeanFactory;
import com.demo.spring.core.ClassScanner;
import com.demo.spring.web.handler.HandlerManager;
import com.demo.spring.web.server.TomcatServer;

import java.util.List;

/**
 * Application
 *
 * @author shengweisong
 * @date 2021-11-24 8:37 PM
 **/
public class MiniApplication {

    public static void run(Class<?> clz, String[] args) {
        TomcatServer tomcatServer = new TomcatServer();

        try {
            // 启动tomcat
            tomcatServer.startServer();
            // 记载实例Bean
            List<Class<?>> classList = ClassScanner.scannerClasses(clz.getPackage().getName());
            BeanFactory.initBean(classList);
            // 绑定path 以及对应的实现类
            HandlerManager.resolveMappingHandler(classList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}