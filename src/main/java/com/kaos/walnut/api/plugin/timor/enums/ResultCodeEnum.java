package com.kaos.walnut.api.plugin.timor.enums;

import com.kaos.walnut.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCodeEnum implements Enum {
    success("0", "服务正常"),
    fail("-1", "服务出错");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}
