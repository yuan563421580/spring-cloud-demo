package com.yuansb.spring.cloud.demo.service;

import com.yuansb.spring.cloud.demo.factory.ConsumeFeignClientFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 这个接口相当于把原来的服务提供者项目当成一个 Service 类
 *
 *  通过注解 @FeignClient("SERVER-PROVIDER") 要调用的服务名称
 *      在 @FeignClient 注解中增加 fallbackFactory 属性声明熔断后备工厂类
 */
@FeignClient(value = "SERVER-PROVIDER", fallbackFactory = ConsumeFeignClientFactory.class)
//@RequestMapping("/provider")
public interface ConsumeFeignClient {

    /**
     * 相当于以方法的形式实现调用 /provider/hi
     */
    @GetMapping("/provider/hi")
    public String sayHi();

    /**
     * 相当于以方法的形式实现调用 /provider/testNum/{num}
     * @param num
     * @return
     */
    @GetMapping("/provider/testNum/{num}")
    public String testNum(@PathVariable("num") Integer num);

}
