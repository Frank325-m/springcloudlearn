package com.learn.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients; // 开启Feign
import org.springframework.context.annotation.Bean;

import com.alibaba.cloud.sentinel.SentinelWebAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(exclude = {SentinelWebAutoConfiguration.class})
@EnableDiscoveryClient  // 注册到Nacos
@EnableFeignClients(basePackages="com.learn.order.feign") // 扫描feign包
@MapperScan("com.learn.order.mapper") // 扫描mapper
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
        log.info("===== Order Service Start! Port is 8082 =====");
    }

    // 手动注册SentinelWebInterceptor，替代自动配置
    @Bean
    @ConditionalOnWebApplication
    public SentinelWebInterceptor sentinelWebInterceptor() {
        return new SentinelWebInterceptor();
    }
}