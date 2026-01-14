package com.learn.order.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

@Component
public class SentinelRuleChecker {
    @Bean
    public CommandLineRunner checkSentinelRules() {
        return args -> {
            // 打印已加载的限流规则
            System.out.println("=== 已加载的Sentinel限流规则 ===");
            FlowRuleManager.getRules().forEach(rule -> {
                System.out.println("资源名：" + rule.getResource() + "，QPS阈值：" + rule.getCount());
            });
            // 若为空，说明规则未加载；若有内容，说明规则加载成功（需再验证匹配）
        };
    }
}
