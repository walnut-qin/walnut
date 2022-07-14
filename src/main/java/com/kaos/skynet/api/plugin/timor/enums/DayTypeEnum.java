package com.kaos.skynet.api.plugin.timor.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日期类型
 */
@Getter
@AllArgsConstructor
public enum DayTypeEnum implements Enum {
    工作日("0", "工作日"),
    周末("1", "周末"),
    节日("2", "节日"),
    调休("3", "调休");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}
