package com.learn.order.entity;

import lombok.Data;

// 用于封装订单+用户的组合返回数据
@Data
public class OrderUserOV {
    private Order order; // 订单信息
    private User user;   // 关联的用户信息
}
