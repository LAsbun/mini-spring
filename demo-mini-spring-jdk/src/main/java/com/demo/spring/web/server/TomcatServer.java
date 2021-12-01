package com.demo.spring.web.server;

import com.demo.spring.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @author shengweisong
 * @date 2021-11-24 8:44 PM
 **/
public class TomcatServer {
    private Tomcat tomcat;

    public void startServer() throws LifecycleException {

        // 实例化，并绑定端口
        tomcat = new Tomcat();
        tomcat.getConnector().setURIEncoding("UTF-8");
        tomcat.setPort(8080);
        tomcat.start();

        // 设置servlet类
        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        // 这里就是核心外部请求
        DispatcherServlet servlet = new DispatcherServlet();
        Tomcat.addServlet(context, "dispatchServlet", servlet)
                .setAsyncSupported(false);

        context.addServletMappingDecoded("/", "dispatchServlet");

        tomcat.getHost().addChild(context);
        // 启动
        Thread tomcatAwaitThread = new Thread("tomcat_await_thread") {

            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };

        // 阻塞，等待请求
        tomcatAwaitThread.setDaemon(false);
        tomcatAwaitThread.start();


    }
}