package com.demo.spring.framework.beans.factory.support;

import com.demo.spring.framework.beans.core.convert.ConversionService;
import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.config.BeanDefinition;
import com.demo.spring.framework.beans.factory.ConfigurableBeanFactory;
import com.demo.spring.framework.beans.factory.FactoryBean;
import com.demo.spring.framework.beans.factory.config.BeanPostProcessor;
import com.demo.spring.framework.beans.util.StringValueResolver;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * BeanFactory抽象接口
 *
 * @author shengweisong
 * @date 2021-12-06 8:11 PM
 **/
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    @Getter
    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    @Getter
    private final List<StringValueResolver> stringValueResolverList = new ArrayList<>();

    private ConversionService conversionService;

    @Override
    public Object getBean(String name) throws BeansException {
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            return getObjectForBeanInstance(sharedInstance, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition);
        return getObjectForBeanInstance(sharedInstance, name);
    }

    // 创建实例Bean
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);

    //
    protected abstract BeanDefinition getBeanDefinition(String name);

    protected abstract boolean containsBeanDifinition(String beanName);

    @Override
    public boolean containsBean(String beanName) {
        return containsBeanDifinition(beanName);
    }

    /**
     * 在这里特殊判断是否是由FactoryBean创建
     */
    private Object getObjectForBeanInstance(Object sharedInstance, String beanName) {

        Object object = sharedInstance;

        if (sharedInstance instanceof FactoryBean) {
            FactoryBean factoryBean = (FactoryBean) sharedInstance;

            try {
                if (factoryBean.isSingleton()) {
                    // 注意这里获取的不是FactoryBean实例自身。而且FactoryBean#getObject
                    Object bean = this.factoryBeanObjectCache.get(beanName);
                    if (bean == null) {
                        bean = factoryBean.getObject();
                        this.factoryBeanObjectCache.put(beanName, bean);
                    }

                } else {
                    // 非单例, 直接获取
                    object = factoryBean.getObject();
                }
            } catch (Exception ex) {
                throw new BeansException("FactoryBean throw Exception on Object[" + beanName + "] creation", ex);
            }
        }
        return object;
    }


    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    @Override
    public void addBeanProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessorList.remove(beanPostProcessor);
        this.beanPostProcessorList.add(beanPostProcessor);
    }

    @Override
    public void destroySingletons() {

    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver resolver) {
        this.stringValueResolverList.add(resolver);
    }

    @Override
    public String resolveEmbrddedValue(String value) {
        String result = value;
        for (StringValueResolver stringValueResolver : this.stringValueResolverList) {
            result = stringValueResolver.resolverStringValue(result);
        }

        return result;
    }

    @Override
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public ConversionService getConversionService() {
        return this.conversionService;
    }
}