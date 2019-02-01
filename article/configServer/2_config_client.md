# 关于如何使用ConfigServer
## configServer客户端

在git仓库microservicecloud-config中，新建microservicecloud-config-client.yml，内容如下：
```yaml
spring:
  profiles:
    active: dev

---
server:
  port: 8201
spring:
  profiles: dev
  application:
    name: microservicecloud-client-config

eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/

---

server:
  port: 8202

spring:
  profiles: test
  application:
    name: microservicecloud-client-config

eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/
```
ok~,仓库修改完成，接下来在项目中新建一个module:microservicecloud-config-client-3355,配置中心客户端
在pom.xml文件中我主要是添加这次配置所需要的重要依赖，其他的依赖可以去看项目中具体的pom文件。
> 
     <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-config</artifactId>
      </dependency>

  
  在资源目录下新建bootstrap.yml：
  ```yaml
  spring:
    cloud:
      config:
        name: microservicecloud-config-client #需要从github上读取的资源名称，注意没有yml后缀名
        profile: dev   #本次访问的配置项
        label: master
        uri: http://config3344.com:3344  #本微服务启动后先去找3344号服务，通过SpringCloudConfig获取GitHub的服务地址 这个config3344.com是做过域名映射的
  ```
  新建application.yml文件，这个文件可有可无，为保证统一，这边创建在这，内容为：
  ```yaml
  spring:
    application:
      name: microservicecloud-config-client
  ```
  新建Controller：
  ```java
  @RestController
  public class ConfigClientRest {

      @Value("${spring.application.name}")
      private String applicationName;

      @Value("${eureka.client.service-url.defaultZone}")
      private String eurekaServers;

      @Value("${server.port}")
      private String port;

      @RequestMapping("/config")
      public String getConfig() {
          String str = "applicationName: " + applicationName + "\t eurekaServers:" + eurekaServers + "\t port: " + port;
          System.out.println("******str: " + str);
          return "applicationName: " + applicationName + "\t eurekaServers:" + eurekaServers + "\t port: " + port;
      }
  }
  ```
  启动配置中心服务端microservicecloud-config-3344，启动客户端，启动查看控制台：
  ![结果图](https://github.com/guofeiwu/microservicecloud/blob/master/img/2.png)
  
  从图中可以看出，客户端想服务端请求数据。两个都启动完成之后，访问以下`/config`请求:http://configclient3355.com:8201/config 域名做过本地映射。
    ![结果图](https://github.com/guofeiwu/microservicecloud/blob/master/img/3.png)
    
   若是把bootstrap.yml中的profile改成test，出现的就是microservicecloud-config-client.yml文件中test配置的相关信息。ok~，到此，配置中心客户端完成。
   下面一节将写微服务提供者，消费者通过配置中心读取配置文件。
  
  
