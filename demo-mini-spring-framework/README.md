# demo-mini-spring-framework

spring简化版实现与练习

主要参考: https://github.com/DerekYRC/mini-spring

如果不怎么了解IOC以及AOP，建议先看下module: demo-mini-spring-jdk. 基本就可以了解基本的IOC和AOP原理.

### demo-mini-spring-framework
实现spring最核心的点.

#### refresh函数
最核心的函数。承载了IOC,AOP,listener等启动加载过程。