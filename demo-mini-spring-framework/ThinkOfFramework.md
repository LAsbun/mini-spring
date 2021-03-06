框架涉及思路

从领域的角度来看spring，基本都是围绕着BeanDefinition 进行操作.

## Spring Bean生命周期

```mermaid
graph TD

StartSpring(Spring启动)
BeanInstantiation[Bean实例化]
BeanFieldSet(Bean属性注入)
BeanNameAware(调用BeanNameAware#setBeanName)
BeanFactoryAware(调用BeanFactoryAware#setBeanFactory)
ApplicationContextAware(调用ApplicationContextAware#setApplicationContext)
BeforeBeanPostProcess(调用BeanPostProcess#postProcessBeforeInitialization)
InitializaingBean(调用InitializingBean#afterPropertiesSet)
BeanSelfInit(调用自定义初始化方法)
AfterBeanPostProcess(调用BeanPostProcess#postProcessAfterInitialization)
UsingBean(Bean使用中)
CloseBean(调用DisposableBean#destroy)
DestroySelfFunc(调用自定义销毁方法)
EndBean(Bean被销毁)


StartSpring --> BeanInstantiation 
BeanInstantiation --> BeanFieldSet
BeanFieldSet --> BeanNameAware
BeanNameAware-->BeanFactoryAware
BeanFactoryAware-->ApplicationContextAware
ApplicationContextAware-->BeforeBeanPostProcess
BeforeBeanPostProcess-->InitializaingBean
InitializaingBean-->BeanSelfInit
BeanSelfInit-->AfterBeanPostProcess
AfterBeanPostProcess-->UsingBean
UsingBean-->CloseBean
CloseBean-->DestroySelfFunc
DestroySelfFunc-->EndBean

```

``
StartSpring --> BeanInstantiation --> BeanFieldSet --> BeanNameAware BeanNameAware-->BeanFactoryAware-->ApplicationContextAware-->BeforeBeanPostProcess BeforeBeanPostProcess-->InitializaingBean-->BeanSelfInit-->AfterBeanPostProcess AfterBeanPostProcess-->UsingBean UsingBean-->CloseBean-->DestroySelfFunc-->EndBean
``

核心理解:
1 核心在于BeanFactory#getBean. 剩余的都是在createBean的路上狂奔.

# source handler

## BeanFactory基础构成关系

```text
BeanFactory 最顶级类。定义IOC对外最基础接口
HierarchicalBeanFactory 额外封装了一层接口类，spring框架是提供了getParentBeanFactory&containsLocalBean两个接口，但本质没有额外增加处理逻辑
ListableBeanFactory  获取指定类型的所有实例， 对IOC统一对外二次封装
AutowireCapableBeanFactory 自动注入顶级接口。提供自动注入的一些基础接口

SingletonBeanRegistry 单例Bean最顶级类。定义单例相关基础接口
ConfiguratableBeanFactory 结合BeanFactory和SingletonBeanRegistry 提供convertService, BeanPostProcesser等加载功能


ConfigurableListableBeanFactory 组合IOC对外接口，以及单例对外操作的接口。

总结: 其实主要分为两条线
```

## IOC顶级接口基础实现.

### 实现路径

- public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry public abstract class AbstractBeanFactory
  extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory public abstract class
- AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory public class
- DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory,
  BeanDefinitionRegistry

### 加载BeanDefinition

- Resource 将文件改为输入流. Spring是InputStreamSource -> Rsource
- ResourceLoader 加载Resource
- BeanDefinitionReader 读取Resource输入流，变为reader.
    - XmlBeanDefinitionReader
- 至此从定义Bean类，到加载到BeanFactory Done

### 使用BeanFactory. 即应用

- ApplicationEventPublisher 事件发布
- ApplicationContext 应用管理器
- ConfigurableApplicationContext ApplicationContext扩展
  - AbstractApplicationContext  约定刷新核心流程 refresh函数. 抽象实例工厂
  - AbstractRefreshableApplicationContext  实例BeanFactory. 抽象loadDefinition
  - AbstractXmlApplicationContext   实现loadDefinition 抽象definition来源
  - ClassPathApplicationContext v1 基本的要求 IOC 基本的容器管理  

## AOP
- AOP 实际就是代理模式. 不要想复杂
- java中实现代理
  - JDK(需要实现某个接口)
  - Cglib(final标注的不能代理，因为是继承实现的。)

### Spring AOP实现思想
一些概念
```text
Joinpoint连接点: 指那些被拦截到的点
PointCut(切入点): 对拦截点进行作用的定义
Advice(通知、增强): 对PointCut 进行Before After 异常等通知增强
Target(目标):代理的目标对象
Weaving(织入): 对目标对象增强的点
Aspect(切面): 是切入点和通知的结合
```
简单理解，就是对通过一定的切入点增强，作用于(通过织入)某个Target的JointPoint


- 类加载
- 有参构造函数bean
- 解决循环依赖.
- 思考触发点是从bean正常的生命周期走.

V2 要求
- 