package com.learn.order.common;

import java.io.Serializable;

import lombok.Data;

/**
 * 统一返回结果类
 * 所有接口返回此类型，保证格式统一
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;   // 状态码：200成功，500失败，401未授权
    private String msg;     // 提示信息
    private T data;         // 数据体

    // 成功响应（带数据）
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("operater success");
        result.setData(data);
        return result;
    }

    // 成功响应（无数据）
    public static <T> Result<T> success() {
        return success(null);
    }

    // 失败响应
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
    
}
