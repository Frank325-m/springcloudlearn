USE learn_springcloud;
CREATE TABLE IF NOT EXISTS `t_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
  `amount` DOUBLE NOT NULL COMMENT '订单金额',
  `status` VARCHAR(10) DEFAULT '0' COMMENT '订单状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入测试数据（关联user_id=1）
INSERT INTO `order` (user_id, order_no, amount, status) VALUES (1, 'ORDER20260112001', 99.9, '0');

ALTER TABLE `order` RENAME TO `t_order`;