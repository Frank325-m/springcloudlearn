package com.learn.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.learn.order.entity.User;

/**
 * Feign客户端：调用user-service服务
 * @FeignClient("user-service")：指定要调用的服务名称（Nacos中的服务名）
 */
@FeignClient("user-service")
public interface UserFeignClient {
    
    // 方法签名和用户服务的接口完全一致
    @GetMapping("/user/get/{id}")  // 调用user-service的接口
    User getUserById(@PathVariable("id") Long id);
}
