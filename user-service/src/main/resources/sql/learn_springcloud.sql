CREATE DATABASE IF NOT EXISTS learn_springcloud;

USE learn_springcloud;

CREATE TABLE `t_user` (
  `id` bigint NOT NULL COMMENT '主键',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `age` int DEFAULT NULL COMMENT '年龄',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间（可选）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入测试数据
INSERT INTO `user` VALUES (1, 'zhangsan', '123456', 20, 'zhangsan@test.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 重命名原有表（保留数据）
ALTER TABLE `user` RENAME TO `t_user`;