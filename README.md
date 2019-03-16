# SpringBoot多模块的maven配置
编译器使用idea,参考github：https://github.com/vector4wang/spring-boot-quick/tree/master/quick-modules
# 为什么模块开发
  先举个栗子，同一张数据表，可能要在多个项目中或功能中使用，所以就有可能在每个模块都要搞一个mybatis去配置。如果一开始规定说这张表一定不可以改字段属性，那么没毛病。但是事实上， 一张表从项目开始到结束，不知道被改了多少遍，所以，你有可能在多个项目中去改mybatis改到吐血！<br> 
  在举一个栗子，一个web服务里包含了多个功能模块，比如其中一个功能可能会消耗大量资源和时间，当用户调用这个功能的时候，可能会影响到其他功能的正常使用，这个时候，如果把各个功能模块分出来单独部署，然后通过http请求去调用，至于性能和响应速度，再单独去优化，整个过程会非常的舒服！这也有利于将来的分布式集群(现在的微服务springcloud或dubbo就是模块化编程的最好示例)。<br>
# 主要模块：父模块，子模块
父模块打包方式：```<packaging>pom</packaging> ```<br>
子模块可以分为jar包,和war包
子模块打包方式：```<packaging>jar</packaging> <packaging>war</packaging> ```<br>

# 第1步:创建父类聚合模块
通过选择quickstart快速创建一个maven项目，然后将src文件删除，因为父模块工程只负责管理依赖，不需要任何的代码编码，所以删除掉所有的pom依赖和插件，留下最简单的pom文件即可,注意需要打包为pom!!!<br>


父模块pom.xml代码如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.ouxuxi</groupId>
    <artifactId>springboot-module</artifactId>
    <version>1.0-SNAPSHOT</version>

    <!--设置它的打包方式，自己添加的-->
    <packaging>pom</packaging>
    
</project>
```
# 第2.1步:创建子类模块core类，打包为jar包
只要在父工程模块新建一个quickstartmodule，就行
当你创建完成你会发现在子模块pom中会自动添加了
```xml
<parent>
        <artifactId>springboot-module</artifactId>
        <groupId>com.ouxuxi</groupId>
        <version>1.0-SNAPSHOT</version>
</parent>
 ```
而在父模块中pom.xml会自动添加
```xml
<modules>
        <module>core</module>
</modules>
```
再在子类core.xml中添加打包为jar包：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>springboot-module</artifactId>
        <groupId>com.ouxuxi</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>core</artifactId>
    <packaging>jar</packaging>

</project>
```
# 第2.2步:创建子类模块web类，打包为war包
和2.1不同的是maven选择webapp或者 Spring Initializr，然后打包为war包

# 第3.1步：pom文件的详细配置
全局配置依赖：实现父工程控制所有子工程依赖的效果<br>
在父模块pom中加入:
```xml
<!--全局配置依赖：实现父工程控制所有子工程依赖的效果-->
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.16</version>
        </dependency>
    </dependencies>
 ```
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 




