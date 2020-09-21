package com.yuansb.spring.cloud.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 消费接口
 */
@RestController
@RequestMapping("/consumer")
public class ServerConsumerController {

    //资源路径
    private final String url = "http://127.0.0.1:8001/provider/hi";

    @GetMapping("/hi")
    public String hi() {
        RestTemplate template = new RestTemplate();
        String info = template.getForObject(url, String.class);
        return info;
    }

}
