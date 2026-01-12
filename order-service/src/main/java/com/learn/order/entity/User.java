package com.learn.order.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 用户实体类
 * 对应数据库user表
 */
@Data
@TableName("t_user")  // 关键：指定数据库表名
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;  // 主键（雪花算法生成）
    private String username;  // 用户名
    private String password;  // 密码
    private Integer age;  // 年龄
    private String email;  // 邮箱
    private LocalDateTime createTime;  // 注册时间
    private LocalDateTime updateTime;  // 更新时间
}
