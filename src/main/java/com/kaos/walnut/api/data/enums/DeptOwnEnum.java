package com.kaos.walnut.api.data.enums;

import com.kaos.walnut.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeptOwnEnum implements Enum {
    S("1", "南院区"),
    N("2", "北院区"),
    E("3", "东津院区"),
    W("4", "万山分院");

    /**
     * 数据库值
     */
    private String value;

    /**
     * 序列化值
     */
    private String description;
}
