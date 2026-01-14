package com.learn.order2.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.order2.entity.Order2;
import com.learn.order2.feign.StorageFeignClient;
import com.learn.order2.mapper.Order2Mapper;
import com.learn.order2.service.Order2Service;

import io.seata.spring.annotation.GlobalTransactional;

@Service
public class Order2ServiceImpl extends ServiceImpl<Order2Mapper, Order2> implements Order2Service {
    @Resource
    private Order2Mapper order2Mapper;
    @Resource
    private StorageFeignClient storageFeignClient;

    @Override
    @GlobalTransactional(rollbackFor=Exception.class)
    @Transactional
    public Boolean createOrder(Long userId, Long productId, Integer count) {
        // 1. 本地事务，创建订单（写订单）
        Order2 order2 = new Order2();
        order2.setUserId(userId);
        order2.setProductId(productId);
        order2.setCount(count);
        order2.setAmount(count*10.0); // 模拟计算金额
        order2Mapper.insert(order2);

        // 2. 远程调用，扣减库存（库存服务）
        storageFeignClient.reduceStorage(productId, count);
        
        return true;
    }
}
