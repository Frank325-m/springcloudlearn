package com.learn.order.feign.fallback;

import org.springframework.stereotype.Component;

import com.learn.order.common.Result;
import com.learn.order.entity.User;
import com.learn.order.feign.UserFeignClient;

@Component
public class UserFeignFallback implements UserFeignClient {

    @Override
    public Result<User> getUserById(Long id) {
        // 降级兜底数据
        User user = new User();
        user.setId(id);
        user.setUsername("用户服务暂不可用");
        
        Result<User> r_user = new Result<>();
        r_user.setCode(406);
        r_user.setMsg("用户服务暂不可用");
        r_user.setData(user);
        return r_user;
    }
    
}
