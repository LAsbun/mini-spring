package com.demo.spring.framework.beans.factory.config;

import com.demo.spring.framework.beans.PropertyValues;
import lombok.Data;

import java.util.Objects;

/**
 * BeanDefinition 实例保存Bean的相关信息: 类信息，方法信息，bean属性、bean的scope等
 *
 * @author shengweisong
 * @date 2021-12-03 8:53 PM
 **/
@Data
public class BeanDefinition {

    // 单例
    public static String SCOPE_SINGLETON = "singleton";
    // 原型
    public static String SCOPE_PROTOTYPE = "prototype";

    private Class beanClass;

    // 属性值
    private PropertyValues propertyValues;


    // 初始化函数名称
    private String initMethodName;
    // 销毁函数
    private String destroyMethodName;

    // 默认名称空间为单例
    private String scope = SCOPE_SINGLETON;

    // 默认为单例
    private boolean singleton = true;
    private boolean prototype = false;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeanDefinition o1 = (BeanDefinition) o;
        return beanClass.equals(o1.beanClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanClass);
    }


}