# Spring Cloud学习总结

## 一、新建项目 microservicecloud

新建maven工程，在pom文件中加入如下依赖，需要注意的是，作为父pom，打包方式应选择pom。
主要版本：
> Spring Boot的版本为1.5.9.RELEASE，Spring Cloud的版本为Dalston.SR1


```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.mason.microservicecloud</groupId>
    <artifactId>microservicecloud</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    <packaging>pom</packaging>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <junit.version>4.12</junit.version>
        <log4j.version>1.2.17</log4j.version>
        <lombok.version>1.16.18</lombok.version>
        <spring.cloud.version>Dalston.SR1</spring.cloud.version>
        <spring.boot.version>1.5.9.RELEASE</spring.boot.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring.cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring.boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>1.3.0</version>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>5.0.4</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>1.0.31</version>
            </dependency>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>${junit.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
                <version>${log4j.version}</version>
            </dependency>
            <dependency>
                <groupId>ch.qos.logback</groupId>
                <artifactId>logback-core</artifactId>
                <version>1.2.3</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

ok~，新建module,microservicecloud-api,子项目中添加实体类Dept.
```java
@NoArgsConstructor
//@AllArgsConstructor
@Data
@Accessors(chain=true)
public class Dept implements Serializable// entity --orm--- db_table
{
	private Long 	deptno; // 主键
	private String 	dname; // 部门名称
	private String 	db_source;// 来自那个数据库，因为微服务架构可以一个服务对应一个数据库，同一个信息被存储到不同数据库
	
	public Dept(String dname)
	{
		super();
		this.dname = dname;
	}
}
```
然后对microservicecloud-api子项目进行打包，依次执行以下命令mvn clean， mvn install，或者使用maven插件执行。新建microservicecloud-provider-dept-8001
项目，在pom文件中引入必要的依赖：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud</artifactId>
        <groupId>com.mason.microservicecloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-provider-dept-8001</artifactId>
    <packaging>jar</packaging>
    <dependencies>
        <dependency>
            <groupId>com.mason.microservicecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>
</project>
```
然后新建application.yml文件：
```yaml
server:
  port: 8001

spring:
  application:
    name: microservice-dept
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/dbcloud001?useUnicode=true&characterEncoding=UTF-8
    username: root
    password: '123456.'
    type: com.alibaba.druid.pool.DruidDataSource
    dbcp2:
      min-idle: 5                                           # 数据库连接池的最小维持连接数
      initial-size: 5                                       # 初始化连接数
      max-total: 5                                          # 最大连接数
      max-wait-millis: 200 # 等待连接获取的最大超时时间

mybatis:
  config-location: classpath:mybatis/mybatis.cfg.xml
  type-aliases-package: com.mason.springcloud.entities
  mapper-locations: classpath:mybatis/mapper/**/*.xml
```
新建mybatis和mapper目录，并创建mybatis配置文件mybatis.cfg.xml：
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="cacheEnabled" value="true" /><!-- 二级缓存开启 -->
    </settings>
</configuration>
```
创建数据库dbcloud001，新建表dept并插入数据，SQL语句如下：
```mysql
CREATE DATABASE dbcloud001;
USE dbcloud001;
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `deptno` bigint(100) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dname` varchar(255) NOT NULL NOT NULL COMMENT '部门名称',
  `db_source` varchar(255) NOT NULL COMMENT '数据库名称',
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `dept` VALUES ('1', '开发部', DATABASE());
INSERT INTO `dept` VALUES ('2', '人事部', DATABASE());
INSERT INTO `dept` VALUES ('3', 'mason', DATABASE());

```


新建Dao层，创建DeptDao.java:
```java
package com.mason.springcloud.dao;

import java.util.List;

import com.mason.springcloud.entities.Dept;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptDao
{
	 boolean addDept(Dept dept);
	 Dept findById(Long id);
	 List<Dept> findAll();
}
```
在接口上添加`@Mapper`注解，若添加了此注解，无需在主启动类中添加扫描注解`@ComponentScan`。

新建Service层，创建DeptService.java：
```java
import com.mason.springcloud.entities.Dept;

import java.util.List;
public interface DeptService
{
	 boolean add(Dept dept);
	 Dept get(Long id);
	 List<Dept> list();
}
```
创建DeptService实现类DeptServiceImpl.java:
```java
package com.mason.springcloud.service.impl;

import java.util.List;
import com.mason.springcloud.dao.DeptDao;
import com.mason.springcloud.entities.Dept;
import com.mason.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl implements DeptService
{
	@Autowired
	private DeptDao dao;
	
	@Override
	public boolean add(Dept dept)
	{
		return dao.addDept(dept);
	}
	@Override
	public Dept get(Long id)
	{
		return dao.findById(id);
	}
	@Override
	public List<Dept> list()
	{
		return dao.findAll();
	}
}
```
新建Controller层，创建DeptController.java:
```java
package com.mason.springcloud.controller;

import java.util.List;

import com.mason.springcloud.entities.Dept;
import com.mason.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController
{
	@Autowired
	private DeptService service;

	@RequestMapping(value = "/dept/add", method = RequestMethod.POST)
	public boolean add(@RequestBody Dept dept)
	{
		return service.add(dept);
	}

	@RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
	public Dept get(@PathVariable("id") Long id)
	{
		return service.get(id);
	}

	@RequestMapping(value = "/dept/list", method = RequestMethod.GET)
	public List<Dept> list()
	{
		return service.list();
	}
}
```
启动项目，在浏览器地址栏输入：http://localhost:8001/dept/list
![结果图](https://github.com/guofeiwu/microservicecloud/blob/master/img/4.png)

ok~，说明服务提供端正常，接下来编写服务消费者。






