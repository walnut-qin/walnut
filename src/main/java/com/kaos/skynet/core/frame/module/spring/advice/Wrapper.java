package com.kaos.skynet.core.frame.module.spring.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Wrapper {
    /**
     * 响应码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 响应数据
     */
    private Object data;
}
