package com.learn.user.service.impl;

import javax.annotation.Resource;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.learn.user.entity.User;
import com.learn.user.mapper.UserMapper;
import com.learn.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Resource  // 注入Mapper
    private UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
        // MyBatis-Plus内置方法，根据ID查询
        return userMapper.selectById(id);
    }

    @Override
    public int addUser(User user) {
        // 新增用户
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(User user) {
        // 修改用户
        return userMapper.updateById(user);
    }

    @Override
    public int deleteUser(Long id) {
        // 删除用户
        return userMapper.deleteById(id);
    }

    @Override
    public List<User> getUserList() {
        return userMapper.selectList(Wrappers.emptyWrapper());
    }
}
