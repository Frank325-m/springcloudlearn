package com.learn.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;

@Configuration
public class SentinelConfig implements WebMvcConfigurer {

    // 注册Sentinel Web拦截器，实现接口限流
    @Bean
    public SentinelWebInterceptor sentinelWebInterceptor() {
        SentinelWebMvcConfig config = new SentinelWebMvcConfig();
        // 开启URL限流（按接口路径限流）
        config.setHttpMethodSpecify(true);
        config.setWebContextUnify(true);
        return new SentinelWebInterceptor(config);
    }

    // 添加拦截器到SpringMVC
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sentinelWebInterceptor()).addPathPatterns("/**");
    }
}