package com.learn.order.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.learn.order.common.Result;
import com.learn.order.config.SentinelRuleChecker;
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
    private OrderMapper orderMapper;
    
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private RestTemplate restTemplate;
    @Autowired
    SentinelRuleChecker sentinelRuleChecker;

    @Value("${order.service.desc:默认描述}") // 配置不存在时用默认值
    private String serviceDesc;
    @GetMapping("desc")
    public String getServiceDesc() {
        return "服务描述：" + serviceDesc;
    }

    @GetMapping("/get/{id}")
    @SentinelResource( value = "order-get",
                    blockHandler = "orderGetBlockHandler",
                    fallback = "orderGetFallback")
    public OrderUserOV getOrderById(@PathVariable("id") Long id) {
        sentinelRuleChecker.checkSentinelRules();
        OrderUserOV result = new OrderUserOV();
        Order order = orderMapper.selectById(id);
        if (order == null) {  //return "订单不存在";
            result.setOrder(null);
            result.setUser(null);
        } else {
            // 远程调用user-service（核心：给远程调用加降级）
            // User user = restTemplate.getForObject("http://user-service/user/get/" + order.getUserId(), User.class);
            Result<User> r_user = userFeignClient.getUserById(order.getUserId());
            result.setOrder(order);
            result.setUser(r_user.getData());
        }

        return result;
    }

    // OrderController新增接口
    @GetMapping("/getUserAndOrder/{orderId}")
    public Map<String, Object> getUserAndOrder(@PathVariable Long orderId) {
        // 1.查询订单
        Order order = orderMapper.selectById(orderId);
        // 2.根据订单中的userId调用UserService查询用户
        Result<User> r_user = userFeignClient.getUserById(order.getUserId());
        // 3.组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("user", r_user.getData());
        return result;
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

}
