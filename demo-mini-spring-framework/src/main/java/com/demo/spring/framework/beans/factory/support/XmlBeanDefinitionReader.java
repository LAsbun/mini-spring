package com.demo.spring.framework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.demo.spring.framework.beans.PropertyValue;
import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.config.BeanDefinition;
import com.demo.spring.framework.beans.factory.config.BeanReference;
import com.demo.spring.framework.context.annotation.ClassPathBeanDefinitionScanner;
import com.demo.spring.framework.core.io.Resource;
import com.demo.spring.framework.core.io.ResourceLoader;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author shengweisong
 * @date 2021/12/9
 **/
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";
    public static final String SCOPE_ATTRIBUTE = "scope";
    public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
    public static final String COMPONENT_SCAN_ELEMENT = "component-scan";

    public XmlBeanDefinitionReader(BeanDefinitionRegistery registery) {
        super(registery);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistery registery, ResourceLoader resourceLoader) {
        super(registery, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String location) {
        Resource resource = getResourceLoader().loadResource(location);
        loadBeanDefinitions(resource);

    }

    @Override
    public void loadBeanDefinitions(Resource resource) {
        try {
            InputStream inputStream = resource.getInputStream();
            doLoadBeanDefinitions(inputStream);
        } catch (IOException | DocumentException e) {
            throw new BeansException("loadBeanDefinitions error ", e);
        }
    }

    private void doLoadBeanDefinitions(InputStream inputStream) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);

        Element root = document.getRootElement();

        //  扫描 context-scan路径。
        Element componentScan = root.element(COMPONENT_SCAN_ELEMENT);
        if (componentScan != null) {
            String scanBasePath = componentScan.attributeValue(BASE_PACKAGE_ATTRIBUTE);
            if (StrUtil.isEmpty(scanBasePath)) {
                throw new BeansException(COMPONENT_SCAN_ELEMENT + "." + BASE_PACKAGE_ATTRIBUTE + " value is empty");
            }
            scanPackage(scanBasePath);
        }

        List<Element> beanList = root.elements(BEAN_ELEMENT);

        for (Element bean : beanList) {
            String beanId = bean.attributeValue(ID_ATTRIBUTE);
            String beanName = bean.attributeValue(NAME_ATTRIBUTE);
            String className = bean.attributeValue(CLASS_ATTRIBUTE);
            String initMethodName = bean.attributeValue(INIT_METHOD_ATTRIBUTE);
            String detroyMethodName = bean.attributeValue(DESTROY_METHOD_ATTRIBUTE);
            String beanScope = bean.attributeValue(SCOPE_ATTRIBUTE);

            Class<?> clz;
            try {
                clz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new BeansException("Couldn't find class:" + className);
            }

            //id优先于name
            beanName = StrUtil.isNotEmpty(beanId) ? beanId : beanName;
            if (StrUtil.isEmpty(beanName)) {
                //如果id和name都为空，将类名的第一个字母转为小写后作为bean的名称
                beanName = StrUtil.lowerFirst(clz.getSimpleName());
            }

            BeanDefinition beanDefinition = new BeanDefinition(clz);

            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(detroyMethodName);
            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            List<Element> propertyList = bean.elements(PROPERTY_ELEMENT);
            for (Element property : propertyList) {
                String propertyValueAttribute = property.attributeValue(VALUE_ATTRIBUTE);
                String name = property.attributeValue(NAME_ATTRIBUTE);
                // 引用
                String ref = property.attributeValue(REF_ATTRIBUTE);

                if (StrUtil.isEmpty(name)) {
                    throw new BeansException("beanName is empty");
                }

                Object value = propertyValueAttribute;

                if (StrUtil.isNotEmpty(ref)) {
                    value = new BeanReference(ref);
                }

                PropertyValue propertyValue = new PropertyValue(name, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

            if (getRegister().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName:" + beanName);
            }
            getRegister().registerBeanDefinition(beanName, beanDefinition);


        }


    }

    /**
     * 扫描基础包
     */
    private void scanPackage(String scanBasePath) {
        String[] basePackages = StrUtil.split(scanBasePath, ",");
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegister());
        scanner.doScan(basePackages);
    }
}