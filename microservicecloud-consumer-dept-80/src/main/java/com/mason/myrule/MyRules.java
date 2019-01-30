package com.mason.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mason
 * @version v1.0
 * @since 2019/1/29
 */
@Configuration
public class MyRules {

    /**
     * 自定义rule只要继承AbstractLoadBalancerRule类
     */
    @Bean
    public IRule MyRule() {
        return new RandomRule();
    }

}
