package com.learn.storage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.storage.entity.Storage;
import com.learn.storage.mapper.StorageMapper;
import com.learn.storage.service.StorageService;

@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {
    
    @Resource
    private StorageMapper storageMapper;

    // 本地事务 （seata会管理这个本地事务）
    @Override
    @Transactional
    public void reduceStorage(Long productId, Integer count) {
        // 扣减库存
        storageMapper.reduceStorage(productId, count);
        // 模拟库存不足异常（用于测试事务回滚）
        Storage storage = this.getOne(lambdaQuery().eq(Storage::getProductId, productId));
        if (storage.getResidue() < 0) {
            throw new RuntimeException("库存不足，扣减失败");
        } 
    }
}
