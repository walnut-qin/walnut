package com.kaos.walnut.api.data.enums;

import com.kaos.walnut.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidStateEnum implements Enum {
    停用("0", "停用"),
    在用("1", "在用"),
    废弃("2", "废弃");

    /**
     * 数据库值
     */
    private String value;

    /**
     * 序列化值
     */
    private String description;
}