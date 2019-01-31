package com.mason.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class DeptConsumerHystrixDashboard_App {
    public static void main(String[] args) {
        SpringApplication.run(DeptConsumerHystrixDashboard_App.class, args);
    }
}
