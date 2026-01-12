package com.learn.user.service;

import java.util.List;

import com.learn.user.entity.User;

/**
 * 用户业务层接口
 */
public interface UserService {

    /**
     * 根据ID查询用户
     */
    User getUserById(Long id);

    /**
     * 新增用户
     */
    int addUser(User user);

    /**
     * 修改用户
     */
    int updateUser(User user);

    /**
     * 删除用户
     */
    int deleteUser(Long id);

    /**
     * 获取所有用户列表
     */
    List<User> getUserList();
    
}
