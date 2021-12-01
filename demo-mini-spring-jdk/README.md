# demo-mini-spring-jdk

spring简化版实现与练习

## 重要！！！

本模块大部分代码都是仿照 https://github.com/czwbig/mini-spring. 不同点:

1. package类加载有点区别。

- 原项目是使用gradle编译成jar，加载。
- 本项目直接使用的是maven构建，可以直接运行:com.demo.spring.Application#main函数即可

## 实现思想:

### IOC

1. 扫描对应package下面所有的.class的文件。通过ClassLoader加载
2. 通过遍历所有的类，根据不同的注解`Compont` `Aspect`加载实例.
    1. 先加载所有类。记录需要依赖其他类注入的类`Autowired1`修饰的filed
    2. 加载AOP相关的切面类。这里主要的作用就是对需要做切面的方法，进行代理
    3. 重新加载一次需要注入的类。(因为1中注入的应该是null)
    4. 完成Bean初始化.
3. 特别的注意
    1. 有两个全局变量：
        1. `beans` 所有的实例bean
        2. `beanHasAutoWiredField` 需要注入的filed
    2. 实例化的时候都是实例的无参构造函数。所有本项目对于一些有参构造函数还是支持不了。但是原理都是一样的，只是实现复杂度不同

### AOP

1. AOP的主要实现思想就是对`Aspect`修饰的类中的方法，进行代理
2. 其中
    1. PointCut标识需要修饰的函数
    2. After,Before修饰PointCut修饰的类.
    3. 进行判断是否需要额外修饰的时候，是根据函数名称进行判断的。

### 核心模块

```text
src/main/java/com/demo/spring
├── aop
│   ├── After.java  # AOP After注解
│   ├── Aspect.java # AOP 切面注解。 AOP相关相关类需要有个class注解
│   ├── Before.java # AOP After注解
│   ├── Pointcut.java # AOP 切面
│   └── ProxyDynamic.java # 动态代理。用来代理切面类
├── bean
│   ├── AutoWired.java  # 注入注解
│   ├── BeanFactory.java # 核心类。负责加载单例，以及将实例注入到AutoWired中
│   └── Component.java # 使用这个修饰的类，都会被当做单例实例化
├── core
│   └── ClassScanner.java  # 核心类。扫描类下面的类，判断是否需要实例化
├── starter
│   └── MiniApplication.java # 框架的启动类，初始化类
└── web  # mvc的一些注解.
    ├── handler
    │   ├── HandlerManager.java  w
    │   └── MappingHandler.java
    ├── mvc
    │   ├── Controller.java
    │   ├── RequestMapping.java
    │   └── RequestParam.java
    ├── server
    │   └── TomcatServer.java
    └── servlet
        └── DispatcherServlet.java

```