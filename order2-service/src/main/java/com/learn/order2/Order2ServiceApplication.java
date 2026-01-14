package com.learn.order2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.learn.order2.mapper")
@EnableFeignClients(basePackages = "com.learn.order2.feign") // 关键：开启Feign并指定扫描包
public class Order2ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(Order2ServiceApplication.class, args);
        System.out.println("Order2ServiceApplication start OK");
    }
}