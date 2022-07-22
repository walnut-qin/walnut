package com.walnut.core.type.enums;

import com.walnut.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexEnum implements Enum {
    Male("M", "男"),
    Female("F", "女"),
    Unknown("U", "不详");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}
