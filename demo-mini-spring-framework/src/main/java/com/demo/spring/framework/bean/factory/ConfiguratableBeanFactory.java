package com.demo.spring.framework.bean.factory;

/**
 * 结合BeanFactory和SingletonBeanRegistry
 * <p>
 * PostProcessor等可插拔接口的顶级约定类。
 * PS: 继承了SingletonBeanRegistry，即表示可以对Bean进行核心的操作
 *
 * @author shengweisong
 * @date 2021-12-03 5:04 PM
 **/
public interface ConfiguratableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    /**
     * 单例Scopr
     */
    String SCOPE_SINGLETON = "singleton";

    /**
     * 原型scope
     */
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * bean额外生命周期 Before & After. BeanPostProcessor
     * addBeanPostProcessor
     */

    /**
     *  destorySingtons; 在工厂关闭的时候被调用.
     *  destroySingletons
     */

    /**
     * 对字符串处理的简单策略
     * addEmbeddedValueResolver
     */

    /**
     * 对字符串处理
     * resolveEmbeddedValue
     */

    /**
     * 类型转换 ConvertService
     * addConvertService
     */

    /**
     * 对字符串处理
     * resolveConvertService
     */


}