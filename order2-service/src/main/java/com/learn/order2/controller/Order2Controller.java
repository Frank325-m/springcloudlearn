package com.learn.order2.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.order2.service.Order2Service;

@RestController
@RequestMapping("/order2")
public class Order2Controller {
    @Resource
    private Order2Service order2Service;

    // 下单接口（触发分布式事务）
    @PostMapping("/create")
    public String createOrder(
        @RequestParam Long userId,
        @RequestParam Long productId,
        @RequestParam Integer count
    ) {
        try {
            order2Service.createOrder(userId,productId, count);
            return "下单成功";
        } catch (Exception e) {
            return "下单失败：" + e.getMessage();
        }
    }
}
