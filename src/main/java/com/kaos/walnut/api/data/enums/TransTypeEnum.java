package com.kaos.walnut.api.data.enums;

import com.kaos.walnut.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransTypeEnum implements Enum {
    Positive("1", "正交易"),
    Negative("2", "负交易");

    /**
     * 数据库值
     */
    private String value;

    /**
     * 序列化值
     */
    private String description;
}
