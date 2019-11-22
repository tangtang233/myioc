# 手撸IoC框架
原文地址：[https://www.xilidou.com/2018/01/08/spring-ioc/][犀利豆的博客]

GitHub地址：[https://github.com/diaozxin007/xilidou-framework][xilidou-framework]

## 本项目是
- **为了深入了解IoC原理，参照前辈的手撸框架代码，从头到尾手敲了一遍，特此放在自己的repo中，供以后查看**

## spirng 的 IOC 是怎么实现的
- 了解Spring框架最直接的方法就阅读Spring的源码。但是Spring的代码抽象的层次很高，且处理的细节很高。对于大多数人来说不是太容易理解。我读了Spirng的源码以后以我的理解做一个总结,Spirng IoC 主要是以下几个步骤。

1. 初始化 IoC 容器。
2. 读取配置文件。
3. 将配置文件转换为容器识别对的数据结构（这个数据结构在Spring中叫做 BeanDefinition） 
4. 利用数据结构依次实例化相应的对象
5. 注入对象之间的依赖关系


[xilidou-framework]: https://github.com/diaozxin007/xilidou-framework

[犀利豆的博客]: https://www.xilidou.com/2018/01/08/spring-ioc/