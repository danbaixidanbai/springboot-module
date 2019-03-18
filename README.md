# SpringBoot多模块的maven配置整合mybatis
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

# 第3步：pom文件的详细配置
## 父工程pom.xml详细配置
 需要加入springboot提供的父工程：
 
  ```xml
  <!-- 继承说明：这里继承SpringBoot提供的父工程 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.0.RELEASE</version>
        <relativePath/>
    </parent>
  ```
  
全局配置依赖：实现父工程控制所有子工程依赖的效果<br>
第一种方法：在父模块pom中加入:<br>
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
 
第二种方法：通过<dependcyManagent>:可以把所有子模块用到的依赖都声明在此包括版本号，然后子模块需要显式的引用但不需要配置版本号！！！<br>
  ```xml
   <!-- dependencyManagement 可以把所有子模块用到的依赖都声明在此包括版本号，然后子模块需要显式的引用但不需要配置版本号！！！ -->
    <dependencyManagement>
        <dependencies>
            <!--子模块core-->
            <dependency>
                <groupId>com.ouxuxi</groupId>
                <artifactId>core</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
            <!--子模块web-->
            <dependency>
                <groupId>com.ouxuxi</groupId>
                <artifactId>web</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <!--热部署配置-->
            <dependency>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-devtools</artifactId>
                 <version>2.0.4.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>1.3.1</version>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>8.0.11</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
 ```
  
  ## 子工程pom.xml详细配置
  1.core工程pom.xml配置：<br>
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
    <dependencies>
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    </dependencies>
</project>
 ```
 2.web工程pom.xml配置：<br>
  注意：需要在依赖dependcy中导入core依赖<br>
  ```xml
<!--子模块core-->
        <dependency>
            <groupId>com.ouxuxi</groupId>
            <artifactId>core</artifactId>
        </dependency>
```
 web工程pom.xml详细配置为：<br>
 ```xml
 <?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>springboot-module</artifactId>
        <groupId>com.ouxuxi</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>web</artifactId>
  
    <packaging>war</packaging>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.7</maven.compiler.source>
        <maven.compiler.target>1.7</maven.compiler.target>
    </properties>

    <dependencies>

        <!--子模块core-->
        <dependency>
            <groupId>com.ouxuxi</groupId>
            <artifactId>core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.1.0.RELEASE</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>

    <build>
        <finalName>web</finalName>
        <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <!--指定mainClass-->
                    <configuration>
                        <mainClass>com.WebApplication</mainClass>
                    </configuration>
                </plugin>

                <plugin>
                    <artifactId>maven-clean-plugin</artifactId>
                    <version>3.1.0</version>
                </plugin>
                <!-- see http://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_war_packaging -->
                <plugin>
                    <artifactId>maven-resources-plugin</artifactId>
                    <version>3.0.2</version>
                </plugin>
                <plugin>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.8.0</version>
                </plugin>
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>2.22.1</version>
                </plugin>
                <plugin>
                    <artifactId>maven-war-plugin</artifactId>
                    <version>3.2.2</version>
                </plugin>
                <plugin>
                    <artifactId>maven-install-plugin</artifactId>
                    <version>2.5.2</version>
                </plugin>
                <plugin>
                    <artifactId>maven-deploy-plugin</artifactId>
                    <version>2.8.2</version>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
</project>
 ```
 # 第4步：core工程实体类，dao层，service层代码实现(可以将每层再细分为更小的子模块，这里偷懒)
 ## 数据库为user,表为users;
 ## 4.1：实体类实现：
 在core工程/src/main/java/com/entity下新建User：
 
 ```java
 package com.entity;
 
public class User {
    private int id;
    private String userName;
    private String password;

    public User() {
    }
    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
```

  在core工程/src/main/java/com/common下新建Result用于返回处理结果：
  ```java
  package com.common;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = -4577255781088498763L;
    private static final int OK = 0;
    private static final int FAIL = 1;
    private static final int UNAUTHORIZED = 2;

    private T data; //服务端数据
    private int status = OK; //状态码
    private String msg = ""; //描述信息

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //APIS
    public static Result isOk(){
        return new Result();
    }
    public static Result isFail(){
        return new Result().status(FAIL);
    }
    public static Result isFail(Throwable e){
        return isFail().msg(e);
    }
    public Result msg(Throwable e){
        this.setMsg(e.toString());
        return this;
    }
    public Result data(T data){
        this.setData(data);
        return this;
    }
    public Result status(int status){
        this.setStatus(status);
        return this;
    }


    //Constructors
    public Result() {

    }

}
```
## 4.2：dao层实现：
在core工程/src/main/java/com/dao下新建接口UserDao:<br>
```java
package com.dao;

import com.entity.User;

import java.util.List;

public interface UserDao {
    public  List<User> getUserList();

}
```
在core工程/src/main/resources/mapper下新建mybatis映射文件：UserDao.xml:<br>
```xml
<?xml version="1.0" encoding="utf-8" ?>
        <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="com.dao.UserDao">
         <select id="getUserList" resultType="com.entity.User">
             select * from users
        </select>
</mapper>
```
 ## 4.3：service层实现：
 在core工程/src/main/java/com/service下新建接口UserService:<br>
 ```java
 package com.service;

import com.common.Result;
import com.entity.User;

import java.util.List;

public interface UserService {

    public Result<List<User>> queryUserList();
}
```
 在core工程/src/main/java/com/service/impl下新建接口实现类UserServiceImpl:<br>
 ```java
package com.service.impl;

import com.common.Result;
import com.dao.UserDao;
import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    @Override
    public Result<List<User>> queryUserList() {
        List<User> userList=userDao.getUserList();
        Result<List<User>> result=new Result<List<User>>();
        if(userList.size()>0){
            result.data(userList);
            result.status(0);
        }else{
            result.status(1);
        }
        return result;
    }
}
```
 # 第5步：web层代码实现
 ## 5.1：controller层实现：
 在web工程/src/main/java/com/controller下新建UserController:<br>
 ```java
 package com.controller;

import com.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    private Result getUserlist(){
        return userService.queryUserList();
    }
}
```
在web工程/src/main/resources下配置springboot配置文件application.yml：<br>
```yml
server:
  port: 8080

mybatis:
  type-aliases-package: com.entity
  mapper-locations: classpath:mapper/*.xml

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT&useSSL=false
    username: root
    password: 123456

```
在web工程/src/main/com下配置springboot启动类WebApplication:<br>
```java
package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.dao")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }
}
```
 
 # 第6步：运行项目
 运行启动类main方法 在浏览器运行<br>
 localhost:8080/user/test若成功则会看到Result的json对象显示在浏览器
  # 第7步：部署项目
  在maven
 
 




