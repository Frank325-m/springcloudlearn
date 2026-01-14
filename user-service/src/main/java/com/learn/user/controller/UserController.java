package com.learn.user.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.learn.user.common.Result;
import com.learn.user.config.SentinelRuleChecker;
import com.learn.user.entity.User;
// import com.learn.user.mapper.UserMapper;
import com.learn.user.service.UserService;


@RestController
@RequestMapping("/user")
@RefreshScope  // 关键：开启配置热更新，Nacos配置修改后自动刷新
public class UserController {

    @Autowired
    SentinelRuleChecker sentinelRuleChecker;

    @Resource
    private UserService userService;

    @Value("${user.service.desc:默认描述}")
    private String serviceDesc;

    @GetMapping("/desc")
    public String getServiceDesc() {
        return "服务描述：" + serviceDesc;
    }

    // @Resource
    // private UserMapper userMapper;

    // 根据ID查询用户 核心接口：添加Sentinel限流+降级
    @GetMapping("/get/{id}")
    @SentinelResource(
        value="user-get", // 资源名（唯一标识）
        blockHandler="userGetBlockHandler",  // 限流兜底方法
        fallback="userGetFallback" // 异常降级兜底方法
    )
    public Result<User> getUserById(@PathVariable Long id) {
        sentinelRuleChecker.checkSentinelRules();

        User user = userService.getUserById(id);
        // User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error(404, "get user failed, the reason is the user is not exist, id:" + id);
        }
        return Result.success(user);
    }

    // 新增用户
    @PostMapping("/add")
    public Result<String> addUser(@RequestBody  User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return Result.error(400, "add user failed, the reason is the username was null");
        }
        int count = userService.addUser(user);
        // int count = userMapper.insert(user);
        return count > 0 ? Result.success("add user success, the id is " + user.getId()) : Result.error(500, "dd user failed");
    }

    // 修改用户
    @PutMapping("/update")
    public Result<String> updateUser(@RequestBody  User user) {
        if (user.getId() == null) {
            return Result.error(400, "update user failed, the reason is the userid was null");
        }
        int count = userService.updateUser(user);
        // int count = userMapper.updateById(user);
        return count > 0 ? Result.success("update user success") : Result.error(500, "update user failed");
    }

    // 删除用户
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        int count = userService.deleteUser(id);
        // int count = userMapper.deleteById(id);
        return count > 0 ? Result.success("delete user success") : Result.error(500, "delete user fialed");
    }

    // 获取所有用户
    @GetMapping("/list")
    public Result<List<User>> getUserList() {
        List<User> users = userService.getUserList();
        // List<User> users = userMapper.selectList(null);
        return Result.success(users);
    }

    // 限流兜底方法（参数和返回值要和原方法一致，额外加BlockException）
    public Result<User> userGetBlockHandler(Long id, BlockException e) {
        Result<User> fallbackUser = new Result<>();
        User user= new User();
        user.setId(id);
        fallbackUser.setCode(406);
        fallbackUser.setMsg("接口访问过于频繁，请稍后再试（限流兜底）");
        return fallbackUser;
    }

    // 异常降级兜底方法（参数和返回值要和原方法一致，额外加Throwable）
    public Result<User> userGetFallback(Long id, Throwable e) {
        Result<User> fallbackUser = new Result<>();
        User user= new User();
        user.setId(id);
        fallbackUser.setCode(407);
        fallbackUser.setMsg("用户服务异常：请稍后再试,(降级兜底）");
        fallbackUser.setData(user);
        return fallbackUser;
    }

}
