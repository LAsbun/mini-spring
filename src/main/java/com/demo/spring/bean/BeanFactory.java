package com.demo.spring.bean;

import com.demo.spring.aop.*;
import com.demo.spring.web.mvc.Controller;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author shengweisong
 * @date 2021-11-24 8:46 PM
 **/
public class BeanFactory {

    private static final Map<Class<?>, Object> beans = new ConcurrentHashMap<>();


    private final static Set<Class<?>> beanHasAutoWiredField = Collections.synchronizedSet(new HashSet<>());

    public static void initBean(List<Class<?>> classList) throws Exception {
        List<? extends Class<?>> classesToCreated = Lists.newArrayList(classList);

        // 切面
        List<Class<?>> aspectClzList = Lists.newArrayList();


        for (Class<?> aClass : classesToCreated) {
            // 切面
            if (aClass.isAnnotationPresent(Aspect.class)) {
                aspectClzList.add(aClass);
            } else {
                createBean(aClass);
            }
        }

        // 切面
        resolveAOP(aspectClzList);

        //
        for (Class<?> aClass : beanHasAutoWiredField) {
            createBean(aClass);
        }
    }

    static Function<String, String> takeToBrackets = (strWithBrackets) -> strWithBrackets.substring(0, strWithBrackets.indexOf("("));


    private static void resolveAOP(List<Class<?>> aspectClzList) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        for (Class<?> aClass : aspectClzList) {
            Method before = null; // 前置动作
            Method after = null; // 后置动作
            String method = null; // 切入方法
            Object target = null;
            String pointcutName = null;

            Object bean = aClass.newInstance();
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.isAnnotationPresent(Pointcut.class)) {
                    String pointCutValue = declaredMethod.getAnnotation(Pointcut.class).value();
                    String classStr = pointCutValue.substring(0, pointCutValue.lastIndexOf("."));
                    target = Thread.currentThread().getContextClassLoader().loadClass(classStr).newInstance();
                    method = pointCutValue.substring(pointCutValue.lastIndexOf(".") + 1);
                    pointcutName = declaredMethod.getName();

                }
            }

            if (pointcutName == null) {
                continue;
            }

            for (Method declaredMethod : bean.getClass().getDeclaredMethods()) {
                if (declaredMethod.isAnnotationPresent(Before.class)) {
                    String value = declaredMethod.getAnnotation(Before.class).value();
                    if (takeToBrackets.apply(value).equals(pointcutName)) {
                        before = declaredMethod;
                    }
                } else if (declaredMethod.isAnnotationPresent(After.class)) {
                    String value = declaredMethod.getAnnotation(After.class).value();
                    if (takeToBrackets.apply(value).equals(pointcutName)) {
                        after = declaredMethod;
                    }
                }
            }

            Object proxy = new ProxyDynamic().createProxy(bean, before, after, target, takeToBrackets.apply(method));

            BeanFactory.beans.put(target.getClass(), proxy);
        }

    }

    public static Object getBean(Class<?> aClass) {

        return beans.get(aClass);

    }

    private static void createBean(Class<?> aClass) throws InstantiationException, IllegalAccessException {
        if (!aClass.isAnnotationPresent(Component.class)
                && !aClass.isAnnotationPresent(Controller.class)) {
            return;
        }

        Object bean = aClass.newInstance();

        for (Field declaredField : aClass.getDeclaredFields()) {
            if (!declaredField.isAnnotationPresent(AutoWired.class)) {
                continue;
            }

            BeanFactory.beanHasAutoWiredField.add(aClass);

            Class<?> fieldType = declaredField.getType();

            declaredField.setAccessible(true);
            if (fieldType.isInterface()) {
                for (Class<?> key : BeanFactory.beans.keySet()) {
                    if (fieldType.isAssignableFrom(key)) {
                        fieldType = key;
                        break;
                    }
                }

            }

            declaredField.set(bean, BeanFactory.getBean(fieldType));
        }

        BeanFactory.beans.put(aClass, bean);


    }
}