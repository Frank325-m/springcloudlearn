package com.learn.order.feign;

import org.springframework.web.bind.annotation.GetMapping;

public interface StorageFeignClient {

    @GetMapping("/user/save") 
    Boolean reduceStorage(String key, Integer num);
}
