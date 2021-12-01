package com.demo.spring.web.handler;

import com.demo.spring.web.mvc.Controller;
import com.demo.spring.web.mvc.RequestMapping;
import com.demo.spring.web.mvc.RequestParam;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * @author shengweisong
 * @date 2021-11-24 8:47 PM
 **/
public class HandlerManager {
    public static List<MappingHandler> mappingHandlerList = Lists.newArrayList();

    public static void resolveMappingHandler(List<Class<?>> classList) {
        classList.stream().filter(aClass -> aClass.isAnnotationPresent(Controller.class))
                .forEach(HandlerManager::parseHanderFromController);
    }

    private static void parseHanderFromController(Class<?> aClass) {
        Arrays.stream(aClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> {
                    String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
                    String[] paramsStr = Arrays.stream(method.getParameters())
                            .filter(parameter -> parameter.isAnnotationPresent(RequestParam.class))
                            .map(parameter -> parameter.getAnnotation(RequestParam.class).value())
                            .toArray(String[]::new);

                    MappingHandler mappingHandler = new MappingHandler(uri, aClass, method, paramsStr);
                    HandlerManager.mappingHandlerList.add(mappingHandler);
                });
    }
}