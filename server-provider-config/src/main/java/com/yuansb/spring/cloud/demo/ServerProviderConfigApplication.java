package com.yuansb.spring.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 启动类
 *
 * 通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client
 */
@EnableEurekaClient
@SpringBootApplication
public class ServerProviderConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerProviderConfigApplication.class, args);
    }

}
