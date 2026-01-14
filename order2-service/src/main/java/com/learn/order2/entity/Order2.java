package com.learn.order2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_order_2")
public class Order2 {
    @TableId(type = IdType.AUTO)
    private Long id; // id BIGINT AUTO_INCREMENT PRIMARY KEY,
    private Long userId; // user_id BIGINT NOT NULL,
    private Long productId; // product_id BIGINT NOT NULL,
    private Integer count; // count INT NOT NULL COMMENT '购买数量',
    private Double amount; // amount DECIMAL(10,2) NOT NULL COMMENT '订单金额',
    private String status; // status TINYINT DEFAULT 0 COMMENT '0待支付 1已支付'
}