package com.kaos.walnut.api.data.his.enums;

import com.kaos.walnut.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidEnum implements Enum {
    INVALID("0", "无效"),
    VALID("1", "有效"),
    DISCARD("2", "废弃");

    private String value;

    private String description;
}
