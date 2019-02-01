# 关于如何使用ConfigServer
## configServer服务端

在我自己的github中新建了一个仓库microservicecloud-config，[microservicecloud-config仓库地址](https://github.com/guofeiwu/microservicecloud-config)在仓库中新建application.yml文件，文件的内容如下：
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
![结果图](https://github.com/guofeiwu/microservicecloud/blob/master/img/1.png)

ok~,如果看到这个说明配置中心服务端已经完成，这里只是一个简单的实例，若想要获取更多信息请移步[官网](https://cloud.spring.io/spring-cloud-static/Finchley.SR2/single/spring-cloud.html#_spring_cloud_config_server),顺带提一下url的组合方式：
> - /{application}-{profile}.yml
> - /application/{profile}/label
> - /{label}/{application}-{profile}.yml
> - {application}, which maps to spring.application.name on the client side.-
> - {profile}, which maps to spring.profiles.active on the client (comma-separated list).
> - {label}, which is a server side feature labelling a "versioned" set of config files.

> 注意，在application.yml中我只配置了git的uri，会发现获取不到仓库，Request processing failed; nested exception is java.lang.IllegalStateException: Cannot clone or checkout repository,这里我给出一个方案，在本地生成ssh key，将公钥上传到github中即可。或者在那个修改yml文件为：
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
          uri: https://github.com/guofeiwu/microservicecloud-config.git #GitHub上面的git仓库名字
          username: xxxx # 自己github上面的用户名
          password: xxxx # 自己github上面的密码
```
但是这样会暴露用户名密码，这样不安全，接下来，将配置配置中心客户端。
[configServer客户端配置步骤](https://github.com/guofeiwu/microservicecloud/blob/master/article/configServer/2_config_client.md)

