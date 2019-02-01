package com.mason.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author Mason
 * @version v1.0
 * @since 2019/1/31
 */


@SpringBootApplication
@EnableConfigServer
public class DeptConfig3344_App {
    public static void main(String[] args) {
        SpringApplication.run(DeptConfig3344_App.class, args);
    }
}
