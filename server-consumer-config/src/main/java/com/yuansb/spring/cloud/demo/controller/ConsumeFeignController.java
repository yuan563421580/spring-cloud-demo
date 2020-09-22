package com.yuansb.spring.cloud.demo.controller;

import com.yuansb.spring.cloud.demo.service.ConsumeFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class ConsumeFeignController {

    @Autowired
    private ConsumeFeignClient consumeFeignClient;

    /**
     * 此处的 Mapping 是一级 Controller，
     *      调用方法里边绑定了二级的 Controller，相当于用 Http 完成一次转发
     * @return
     */
    @GetMapping("/feign/hi")
    public String hi() {
        String info = consumeFeignClient.sayHi();
        return info;
    }

    /**
     * 此处的 Mapping 是一级 Controller，
     *      调用方法里边绑定了二级的 Controller，相当于用 Http 完成一次转发
     * @return
     */
    @GetMapping("/feign/testNum/{num}")
    public String testNum(@PathVariable("num") Integer num) {
        String info = consumeFeignClient.testNum(num);
        return info;
    }

}
