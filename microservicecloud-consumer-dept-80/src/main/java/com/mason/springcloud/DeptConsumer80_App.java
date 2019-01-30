package com.mason.springcloud;

import com.mason.myrule.MyRules;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

@SpringBootApplication
@EnableEurekaClient
//@RibbonClient(name = "MICROSERVICE-DEPT", configuration = MyRules.class)
@RibbonClient(name = "microservice-dept", configuration = MyRules.class) // 若是小写名称，Controller中的url中的也要是小写。
//@RibbonClients(value = {@RibbonClient(name = "MICROSERVICE-DEPT", configuration = MyRules.class)})
public class DeptConsumer80_App {
    public static void main(String[] args) {
        SpringApplication.run(DeptConsumer80_App.class, args);
    }
}
