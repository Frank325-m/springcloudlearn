package com.learn.order.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.learn.order.entity.Order;
import com.learn.order.entity.OrderUserOV;
import com.learn.order.entity.User;
import com.learn.order.feign.UserFeignClient;
import com.learn.order.mapper.OrderMapper;

@RestController
@RequestMapping("/order")
@RefreshScope
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private OrderMapper orderMapper;
    
    @Resource
    private UserFeignClient userFeignClient;

    @Value("${order.service.desc:默认描述}") // 配置不存在时用默认值
    private String serviceDesc;

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

    @GetMapping("/sget/{id}")
    @SentinelResource(
            value = "order-get",
            blockHandler = "orderGetBlockHandler",
            fallback = "orderGetFallback"
    )
    public String s_getOrderById(@PathVariable("id") Long id) {
        // 查询订单
        Order order = orderMapper.selectById(id);
        // 远程调用user-service（核心：给远程调用加降级）
        // User user = restTemplate.getForObject("http://user-service/user/get/" + order.getUserId(), User.class);
        User user = userFeignClient.getUserById(order.getUserId());
        return "订单信息：" + order + " | 用户信息：" + user;
    }

    // 限流兜底
    public String orderGetBlockHandler(Long id, BlockException e) {
        return "订单接口访问过于频繁，请稍后再试（限流兜底）";
    }

    // 降级兜底（user-service不可用时返回）
    public String orderGetFallback(Long id, Throwable e) {
        Order order = orderMapper.selectById(id);
        return "订单信息：" + order + " | 用户信息：用户服务暂不可用（降级兜底）";
    }

    @GetMapping("desc")
    public String getServiceDesc() {
        return "服务描述：" + serviceDesc;
    }
}
