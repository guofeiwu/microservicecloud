package com.mason.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DeptConfigProvider8001_App {
    public static void main(String[] args) {
        SpringApplication.run(DeptConfigProvider8001_App.class, args);
    }

}
