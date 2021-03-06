package com.demo.spring.framework.context;

import com.demo.spring.framework.beans.exception.BeansException;
import com.demo.spring.framework.beans.factory.BeanFactory;
import com.demo.spring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.demo.spring.framework.beans.factory.config.BeanFactoryPostProcessor;
import com.demo.spring.framework.beans.factory.config.BeanPostProcessor;
import com.demo.spring.framework.context.event.*;
import com.demo.spring.framework.core.convert.ConversionService;
import com.demo.spring.framework.core.io.DefaultResourceLoader;
import com.demo.spring.framework.core.io.Resource;

import java.util.Map;

/**
 * @author shengweisong
 * @date 2021/12/10
 **/
public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {

    private final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "ApplicationEventMulticaster";

    private final String CONVERTION_SERVICE_BEAN_NAME = "convertionService";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void pulishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicaster(event);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    @Override
    public <T> Map<String, T> getBeanOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeanOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void refresh() throws BeansException {

        // Spring???????????????????????????prepareRefresh??? ?????????????????????????????????????????????

        // ??????beanFactory ?????????BeanDefinition
        refreshBeanFactory();

        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // ?????????ApplicationContextAware???????????????ApplicaitonContext
        beanFactory.addBeanProcessor(new ApplicationContextAwarePostProcessor(this));

        // ?????????????????? ??????BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessor(beanFactory);

        // BeanPostProcessor ??????????????????Bean????????????
        // ?????????Spring????????????????????????????????????BeanPostProcessor
        registerBeanPostProcessor(beanFactory);

        // ????????????????????????MessageSource. ???????????????????????????(?????????..)

        // ????????????????????????
        initApplicationEventMulticaster();

        // ???????????????????????????????????????Bean??????
        // ??????SpringWeb???org.springframework.web.context.support.AbstractRefreshableWebApplicationContext
//        onRefresh(); // ?????????

        // ?????????????????????
        registerListeners();
        // ?????????????????????????????????????????????bean
        finishBeanFactoryInitialization(beanFactory);

        // ??????????????????????????????
        finishRefresh();
    }


    private void finishRefresh() {
        pulishEvent(new ContextRefreshedEvent(this));
    }

    private void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {

        if (beanFactory.containsBean(CONVERTION_SERVICE_BEAN_NAME)) {
            Object conversionService = beanFactory.getBean(CONVERTION_SERVICE_BEAN_NAME);
            if (conversionService instanceof ConversionService) {
                beanFactory.setConversionService((ConversionService) conversionService);
            }
        }

        beanFactory.preInstantiateSingletons();
    }

    private void registerListeners() {
        Map<String, ApplicationListener> applicationListenerMap = getBeanFactory().getBeanOfType(ApplicationListener.class);

        for (ApplicationListener applicationListener : applicationListenerMap.values()) {
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }


    }

    private void initApplicationEventMulticaster() {
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(getBeanFactory());
        getBeanFactory().registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeanOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanProcessor(beanPostProcessor);
        }

    }

    private void invokeBeanFactoryPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeanOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }

    }


    @Override
    public void close() {
        doClose();
    }

    private void doClose() {
        pulishEvent(new ContextCloseEvent(this));
        destroyBeans();
    }

    private void destroyBeans() {

        getBeanFactory().destroySingletons();
    }

    @Override
    public void registerShutdownHook() {
        Thread shutdownHook = new Thread() {
            public void run() {
                doClose();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    /**
     * ??????beanFactory
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * ??????beanFactory
     * ???????????????????????????
     *
     * @return
     */
    public abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * ?????????????????????????????????Bean
     */
    protected abstract void onRefresh();

}