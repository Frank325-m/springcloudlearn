package com.learn;

import java.util.concurrent.TimeUnit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户服务启动类
 * 本地验证环境：SpringBoot 3.2.1 + Nacos 2.3.2 + MySQL 8.0
 */
@Slf4j
@EnableDiscoveryClient // 官方标准注解，Maven可解析  // 开启服务注册发现(向Nacos注册)
@MapperScan("com.learn.mapper")   // 扫描Mapper接口
@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) throws InterruptedException {
        // 启动Spring容器
        SpringApplication.run(UserServiceApplication.class, args);

        // 延迟2秒再完成初始化（给Nacos客户端足够时间建立连接）
        TimeUnit.SECONDS.sleep(2);
        log.info("===== User Service Start! Port is 8081 =====");
    }
}