package com.yuansb.spring.cloud.demo.factory;

import com.yuansb.spring.cloud.demo.service.ConsumeFeignClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 后备工厂
 */
@Component
public class ConsumeFeignClientFactory implements FallbackFactory<ConsumeFeignClient> {

    @Override
    public ConsumeFeignClient create(Throwable cause) {
        return new ConsumeFeignClient() {
            @Override
            public String sayHi() {
                return "请求失败了，请重试...!";
            }

            @Override
            public String testNum(Integer num) {
                return "请求失败了，请重试...!";
            }
        };
    }

}
