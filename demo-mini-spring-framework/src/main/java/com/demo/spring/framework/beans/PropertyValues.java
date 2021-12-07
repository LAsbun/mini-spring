package com.demo.spring.framework.beans;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * @author shengweisong
 * @date 2021-12-07 4:55 PM
 **/
public class PropertyValues {

    @Getter
    private final List<PropertyValue> propertyValueList = Lists.newArrayList();

    public void addPropertyValue(PropertyValue propertyValue) {
        for (int i = 0; i < this.propertyValueList.size(); i++) {
            PropertyValue curPv = this.propertyValueList.get(i);
            if (curPv.getName().equals(propertyValue.getName())) {
                // 替换为新值
                this.propertyValueList.set(i, propertyValue);
                return;
            }
        }

        // 否则就直接追加
        this.propertyValueList.add(propertyValue);

    }



}