package com.demo.spring.web.handler;

import com.demo.spring.bean.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author shengweisong
 * @date 2021-11-24 8:57 PM
 **/
public class MappingHandler {

    private final String uri;
    private final Class<?> controller;
    private final Method method;
    private final String[] args;

    public MappingHandler(String uri, Class<?> controller, Method method, String[] paramsStr) {

        this.uri = uri;
        this.controller = controller;
        this.method = method;
        this.args = paramsStr;
    }

    public boolean handle(ServletRequest servletRequest, ServletResponse servletResponse) throws
            IllegalAccessException, InvocationTargetException, IOException {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        if (!this.uri.equals(requestURI)) {
            return false;
        }

        Object[] parameters = new Object[this.args.length];

        for (int i = 0; i < this.args.length; i++) {
            parameters[i] = servletRequest.getParameter(this.args[i]);
        }

        Object controllerBean = BeanFactory.getBean(this.controller);
        Object response = this.method.invoke(controllerBean, parameters);

        servletResponse.setCharacterEncoding("GBK");
        servletResponse.getWriter().println(response.toString());
        return true;
    }
}