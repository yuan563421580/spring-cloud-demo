package com.yuansb.spring.cloud.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
public class ServerProviderController {

    @Value("${server.port}")
    private String port;

    /**
     * 普通测试
     * @return
     */
    @GetMapping(value = "/hi")
    public String sayHi() {
        return "Hello Eureka, i am from port: " + port;
    }

    /**
     * 新增加一个抛出运行时异常的错误
     * @param num
     * @return
     */
    @GetMapping(value = "/testNum/{num}")
    public String testNum(@PathVariable("num") Integer num) {
        if (num == 0) {
            throw new RuntimeException("test failed");
        }

        return "test succeed, the num is: " + num;
    }

}
