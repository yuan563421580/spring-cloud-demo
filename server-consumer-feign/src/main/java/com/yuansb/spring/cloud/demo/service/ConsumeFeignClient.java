package com.yuansb.spring.cloud.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 这个接口相当于把原来的服务提供者项目当成一个 Service 类
 *
 *  通过注解 @FeignClient("SERVER-PROVIDER") 要调用的服务名称
 *  用 RequestMapping 指定是服务提供者的哪个 Controller 提供服务
 */
@FeignClient("SERVER-PROVIDER")
@RequestMapping("/provider")
public interface ConsumeFeignClient {

    /**
     * 相当于以方法的形式实现调用 /provider/hi
     */
    @GetMapping("/hi")
    public String sayHi();

}
