package com.learn.storage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.storage.entity.Storage;

@Mapper
public interface StorageMapper extends BaseMapper<Storage> {
    //
    @Update("UPDATE t_storage SET used=used+ #{count}, residue=residue- #{count} WHERE product_id=#{productId}")
    void reduceStorage(@Param("productId") Long productId, @Param("count") Integer count);
}
