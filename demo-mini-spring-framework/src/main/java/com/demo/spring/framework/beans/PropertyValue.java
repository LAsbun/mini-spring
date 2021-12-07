package com.demo.spring.framework.beans;

import lombok.Getter;

/**
 * @author shengweisong
 * @date 2021-12-07 4:56 PM
 **/
public class PropertyValue {

    /**
     * 属性名称
     */
    @Getter
    private String name;

    /**
     * 属性值
     */
    @Getter
    private Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}