package com.mason.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {

    @Bean
    @LoadBalanced // 负载均衡 客户端
    public RestTemplate newRestTemplate() {
        return new RestTemplate();
    }
}
