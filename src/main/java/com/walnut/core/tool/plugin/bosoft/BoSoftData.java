package com.walnut.core.tool.plugin.bosoft;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoSoftData<T> {
    /**
     * 响应码
     */
    String code;

    /**
     * 错误信息
     */
    String message;

    /**
     * 响应数据
     */
    T data;
}
