package com.learn.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.User;

/**
 * 用户Mapper接口
 * 继承BaseMapper，无需手写CRUD SQL
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 无需写方法，BaseMapper已包含selectById/insert/updateById/deleteById等
}
