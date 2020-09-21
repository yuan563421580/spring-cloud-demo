package com.yuansb.spring.cloud.demo;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 启动类
 *
 *   通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client
 *   如何使用Ribbon : 为 RestTemplate 配置类添加 @LoadBalanced 注解即可 ， 默认为轮询策略
 *                   修改为随机策略，IRule ： new RandomRule()
 */
@EnableEurekaClient
@SpringBootApplication
public class ServerConsumerRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerConsumerRibbonApplication.class, args);
    }

    @Bean
    @LoadBalanced //开启负载均衡的功能
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    //修改默认的负载均衡策略
    /*@Bean
    public IRule myRule() {
        //随机策略
        return new RandomRule();
    }*/

}
