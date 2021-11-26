package com.demo.spring.web.servlet;

import com.demo.spring.web.handler.HandlerManager;
import com.demo.spring.web.handler.MappingHandler;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author shengweisong
 * @desc 处理网络请求
 * @date 2021-11-24 8:51 PM
 **/
public class DispatcherServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        for (MappingHandler mappingHandler : HandlerManager.mappingHandlerList) {

            try {
                if (mappingHandler.handle(servletRequest, servletResponse)) {
                    return;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                servletResponse.getWriter().println("failed");
            }

        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}