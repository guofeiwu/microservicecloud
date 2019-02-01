# microservicecloud
## spring cloud 学习
+ eureka服务注册与发现
+ Ribbon负载均衡
+ Feign负载均衡
+ hystrix 服务熔断与服务降级
+ hystrix dashboard
+ 网关路由zuul
+ configServer


---------------------------------------------------------------


# 关于如何使用ConfigServer

在我自己的github中新建了一个仓库microservicecloud-config，里面有一个application.yml文件，文件的内容如下：
```yaml
spring:
  profiles:
    active: dev
---
spring:
  profiles: dev
  application:
    name: microservicecloud-dept-config-dev
---
spring:
  profiles: test
  application:
    name: microservicecloud-dept-config-test
```
ok~，仓库暂时先这样，在项目中新建module：microservicecloud-config-3344，配置中心服务端。

除了spring-boot相关的依赖之外还需要config的依赖，其他的依赖可以查看具体的pom，在pom.xml中添加：
>   
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
    
接下来配置application.yml：
```yaml
server:
  port: 3344

spring:
  application:
    name: microservicecloud-config
  cloud:
    config:
      server:
        git:
          uri: git@github.com:guofeiwu/microservicecloud-config.git #GitHub上面的git仓库名字
```

配置完成之后在主启动类中添加注解 **_@EnableConfigServer_** 标识此微服务为配置中心服务端。

首先做一个域名映射，不做也可以：127.0.0.1 config3344.com，启动完成之后，在浏览器输入：http://config3344.com:3344/application-dev.yml，
然后可以看到如下信息：


