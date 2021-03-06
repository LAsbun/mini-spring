package com.demo.spring.framework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.demo.spring.framework.beans.PropertyValue;
import com.demo.spring.framework.beans.PropertyValues;
import com.demo.spring.framework.core.convert.ConversionService;
import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.AutowireCapableBeanFactory;
import com.demo.spring.framework.beans.factory.BeanFactoryAware;
import com.demo.spring.framework.beans.factory.DisposableBean;
import com.demo.spring.framework.beans.factory.InitializingBean;
import com.demo.spring.framework.beans.factory.config.BeanDefinition;
import com.demo.spring.framework.beans.factory.config.BeanPostProcessor;
import com.demo.spring.framework.beans.factory.config.BeanReference;
import com.demo.spring.framework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.lang.reflect.Method;

/**
 * @desc AutowireBeanFactory抽象impl
 * @Date 2021/12/7
 * Created by shengweisong
 */

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();


    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        //尝试在bean创建之前获取快捷方式创建的bean
        Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
        if (bean != null) {
            return bean;
        }
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * 这个方法的作用: 在实例化之前检查是否有通过其他快捷方式(InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation)创建的bean.
     */
    private Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantialization(beanDefinition.getBeanClass(), beanName);
        if (bean != null) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    // 找到所有的InstantiationAwareBeanPostProcessor接口类,判断是否创建对应的bean
    public Object applyBeanPostProcessorsBeforeInstantialization(Class beanClass, String beanName) throws BeansException {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object bean = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (bean != null) {
                    return bean;
                }
            }
        }

        return null;

    }

    // 找到所有的InstantiationAwareBeanPostProcessor接口类,判断是否创建对应的bean
    public boolean applyBeanPostProcessorsAfterInstantialization(Object bean, String beanName) throws BeansException {
        boolean continueWithPropertyPopulation = true;

        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                if (!((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessAfterInstantiation(bean, beanName)) {
                    continueWithPropertyPopulation = false;
                    break;
                }

            }
        }

        return continueWithPropertyPopulation;

    }

    /**
     * 这里做的操作
     * bean的创建生命周期都在这一个函数里面体现.ThinkOfFramework#SpringBean生命周期，using(不含)前的生命周期
     */
    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean;
        try {

            // 实例化
            bean = createInstance(beanDefinition);

            // 这里是为了避免循环依赖，先创建一个factoryBean提前暴露
            if (beanDefinition.isSingleton()) {
                Object finalBean = bean;
                registerSingletonFactory(beanName, new ObjectFactory<Object>() {
                    @Override
                    public Object getObject() throws BeansException {
                        return getEarlyBeanReference(beanName, beanDefinition, finalBean);
                    }
                });
            }

            // 设置bean属性前，判断是否允许修改属性值.
            boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantialization(bean, beanName);
            if (!continueWithPropertyPopulation) {
                return bean;
            }
            // 如果允许，则进行赋值
            applyBeanPostProcessorsBeforeApplyPropertyValues(beanName, bean, beanDefinition);
            // Bean属性注入
            applyPropertyValues(beanName, bean, beanDefinition);
            //初始化 bean
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Throwable e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        //注册有销毁方法的bean
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 因为上面有些循环依赖的类还是为二，三级类，所以需要重新将代理类改为对应实例.
        Object exposedObject = bean;
        if (beanDefinition.isSingleton()) {
            exposedObject = getSingleton(beanName);
            registerSingleton(beanName, exposedObject);
        }

        return exposedObject;

    }

    /**
     *
     */
    private Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object finalBean) {

        Object exposedObject = finalBean;

        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                exposedObject = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference(finalBean, beanName);
                if (exposedObject == null) {
                    return exposedObject;
                }
            }
        }
        return exposedObject;

    }

    /**
     * 将PropertyValues中的值set进Beandifinition.beanclass对应的属性中
     */
    private void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValueList()) {
                String fieldName = propertyValue.getName();
                Object value = propertyValue.getValue();
                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                } else {
                    Class<?> sourceType = value.getClass();
                    Class<?> targetType = (Class<?>) TypeUtil.getFieldType(bean.getClass(), fieldName);
                    ConversionService conversionService = getConversionService();
                    if (conversionService != null) {
                        if (conversionService.canConvert(sourceType, targetType)) {
                            value = conversionService.convert(sourceType, targetType);
                        }
                    }
                }

                BeanUtil.setFieldValue(bean, fieldName, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values for bean [" + beanName + "]", e);
        }

    }

    /**
     * InstantiationAwareBeanPostProcessor#postProcessPropertyValues
     * 将InstantiationAwareBeanPostProcessor中的pvs添加进Beandifinition
     */
    private void applyBeanPostProcessorsBeforeApplyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                PropertyValues propertyValues = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (propertyValues != null) {
                    for (PropertyValue propertyValue : propertyValues.getPropertyValueList()) {
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }

                }
            }
        }

    }

    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (beanDefinition.isSingleton()) {
            if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
                registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
            }
        }
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws Throwable {
        //        BeanNameAware(调用BeanNameAware#setBeanName)
//        BeanFactoryAware(调用BeanFactoryAware#setBeanFactory)
//        ApplicationContextAware(调用ApplicationContextAware#setApplicationContext)
//        BeforeBeanPostProcess(调用BeanPostProcess#postProcessBeforeInitialization)
//        InitializaingBean(调用InitializingBean#afterPropertiesSet)
        // 用来处理BeanNameAware&BeanFactoryAware
        invokeAwareMethods(beanName, bean);

        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 调用bean自身的方法
        invokeInitMethods(beanName, wrappedBean, beanDefinition);

        // BeanPostProcess的后置处理
        applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);

        return wrappedBean;


    }

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Throwable {

        //  判断是否是InitializingBean
        if (wrappedBean instanceof InitializingBean) {
            ((InitializingBean) wrappedBean).afterPropertiesSet();
        }

        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
            if (initMethod == null) {
                throw new BeansException("Error  call initmethod [" + initMethodName + "] in bean [" + beanName + "]");
            }
            initMethod.invoke(wrappedBean);
        }
    }

    /**
     * BeanNameAware
     * FactoryBeanAware
     *
     * @param beanName
     * @param bean
     */
    private void invokeAwareMethods(String beanName, Object bean) {
        // BeanName一般不常用，先不用考虑设置
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
    }

    /**
     * 创建无参实例
     */
    protected Object createInstance(BeanDefinition beanDefinition) {
        // 调用实例策略类创建
        return instantiationStrategy.instantiate(beanDefinition);
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            Object cur = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (cur == null) {
                return result;
            }
            result = cur;
        }

        return result;

    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            Object cur = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (cur == null) {
                return result;
            }
            result = cur;
        }

        return result;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
