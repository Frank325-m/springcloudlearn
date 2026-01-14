package com.learn.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.order.entity.Order;
import com.learn.order.feign.StorageFeignClient;
//import com.learn.order.feign.UserFeignClient;
import com.learn.order.mapper.OrderMapper;
import com.learn.order.service.OrderService;

import io.seata.spring.annotation.GlobalTransactional;

public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    //@Autowired
    //private UserFeignClient userFeignClient;
    @Autowired
    private StorageFeignClient storageFeignClient; // 模拟库存服务

    @Override
    @GlobalTransactional(rollbackFor = Exception.class) // 分布式事务注解
    public Boolean createOrder(Order order) {
        // 1. 创建订单
        this.save(order);
        // 2. 扣减库存（调用库存服务）
        Boolean reduceStorage = storageFeignClient.reduceStorage(order.getOrderNo(), 1);
        if (!reduceStorage) {
            throw new RuntimeException("扣减库存失败，事务回滚");
        }
        // 3. 扣减用户余额（模拟）
        return true;
    }
}
