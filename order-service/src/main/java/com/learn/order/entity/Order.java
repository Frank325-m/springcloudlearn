package com.learn.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_order")  // 关键：指定数据库表名
public class Order {
   private Long id;
   private Long userId;  // 关联用户ID
   private String orderNo; // 订单编号
   private Double amount;  // 订单金额
   private String status;  // 订单状态（0-待支付，1-已支付）
}
