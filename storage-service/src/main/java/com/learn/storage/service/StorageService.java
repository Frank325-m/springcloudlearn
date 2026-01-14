package com.learn.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.storage.entity.Storage;

public interface StorageService extends IService<Storage> {
    // 扣减库存
    void reduceStorage(Long productId, Integer count);
}
