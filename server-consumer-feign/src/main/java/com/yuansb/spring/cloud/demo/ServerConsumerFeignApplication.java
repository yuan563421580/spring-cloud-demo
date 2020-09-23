package com.yuansb.spring.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类
 *
 * 通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client
 * 通过注解 @EnableFeignClients 开启 Feign 功能
 *      @EnableFeignClients("com.yuansb.spring.cloud.demo.service")  //Feign 接口的地址
 *
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ServerConsumerFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerConsumerFeignApplication.class, args);
    }

}
