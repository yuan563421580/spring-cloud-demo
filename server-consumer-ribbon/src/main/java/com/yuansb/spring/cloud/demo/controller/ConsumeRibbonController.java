package com.yuansb.spring.cloud.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consumer")
public class ConsumeRibbonController {

    @Autowired
    private RestTemplate restTemplate;

    //资源路径 注意 URL:端口 换为服务名称
    //http://127.0.0.1:8001/provider/hi 替换为 http://SERVER-PROVIDER/provider/hi
    private final String url = "http://SERVER-PROVIDER/provider/hi";

    @GetMapping("/ribbon/hi")
    public String hi() {
        String info = restTemplate.getForObject(url, String.class);
        return info;
    }

}
