package com.learn.order.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.order.entity.Order;
import com.learn.order.entity.OrderUserOV;
import com.learn.order.entity.User;
import com.learn.order.feign.UserFeignClient;
import com.learn.order.mapper.OrderMapper;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderMapper orderMapper;
    
    @Resource
    private UserFeignClient userFeignClient;

    @GetMapping("/get/{id}")
    public OrderUserOV getOrderById(@PathVariable("id") Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            //return "订单不存在";
            return null;
        }

        User user = userFeignClient.getUserById(order.getUserId());

        OrderUserOV result = new OrderUserOV();
        result.setOrder(order);
        result.setUser(user);

        return result;
    }
}
