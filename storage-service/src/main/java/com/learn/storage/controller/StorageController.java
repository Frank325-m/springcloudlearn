package com.learn.storage.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.storage.service.StorageService;

@RestController
@RequestMapping("/storage")
public class StorageController {
    @Resource
    private StorageService storageService; 

    @PostMapping("/reduce")
    public String reduceStorage(@RequestBody Long productId, @RequestParam Integer count) {
        storageService.reduceStorage(productId, count);
        return "库存扣减成功";
    }
}