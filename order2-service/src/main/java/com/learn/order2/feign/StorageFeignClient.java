package com.learn.order2.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 调用库存服务
@FeignClient(name="storage-service")
public interface StorageFeignClient {
    @PostMapping("/storage/reduce")
    String reduceStorage(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
