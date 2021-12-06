package com.demo.spring.framework.bean.factory.support;

import com.demo.spring.framework.bean.exception.BeansException;
import com.demo.spring.framework.bean.factory.BeanDefinition;
import com.demo.spring.framework.bean.factory.BeanFactory;
import com.demo.spring.framework.bean.factory.ConfiguratableBeanFactory;
import com.demo.spring.framework.bean.factory.FactoryBean;

/**
 * BeanFactory抽象接口
 *
 * @author shengweisong
 * @date 2021-12-06 8:11 PM
 **/
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfiguratableBeanFactory {
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

    protected abstract BeanDefinition getBeanDefinition(String name);

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return null;
    }
}