package com.mason.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Mason
 * @version v1.0
 * @since 2019/1/31
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulGateway9527_App {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGateway9527_App.class, args);
    }
}
