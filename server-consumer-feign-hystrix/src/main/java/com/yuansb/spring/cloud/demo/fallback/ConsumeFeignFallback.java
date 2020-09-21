package com.yuansb.spring.cloud.demo.fallback;

import com.yuansb.spring.cloud.demo.service.ConsumeFeignClient;
import org.springframework.stereotype.Component;

/**
 * 熔断类，
 * 如果请求失败或超时或异常则会触发熔断并返回一个固定结果
 */
@Component
public class ConsumeFeignFallback implements ConsumeFeignClient {

    @Override
    public String sayHi() {
        return "请求失败了，请重试...";
    }

    @Override
    public String testNum(Integer num) {
        return "请求失败了，请重试...";
    }

}
