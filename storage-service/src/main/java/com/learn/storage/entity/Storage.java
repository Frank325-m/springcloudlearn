package com.learn.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_storage")
public class Storage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long productId; // 商品ID
    private Integer total;  // 总库存
    private Integer used;   // 已用库存
    private Integer residue;// 剩余库存
}