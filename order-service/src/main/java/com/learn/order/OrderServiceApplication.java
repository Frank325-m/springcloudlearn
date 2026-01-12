package com.learn.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients; // 开启Feign

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient  // 注册到Nacos
@EnableFeignClients(basePackages="com.learn.order.feign") // 扫描feign包
@MapperScan("com.learn.order.mapper") // 扫描mapper
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
        log.info("===== Order Service Start! Port is 8082 =====");
    }
}