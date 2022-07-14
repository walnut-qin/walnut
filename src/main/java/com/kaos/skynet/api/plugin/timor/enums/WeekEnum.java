package com.kaos.skynet.api.plugin.timor.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeekEnum implements Enum {
    Monday("1", "周一"),
    Tuesday("2", "周二"),
    Wednesday("3", "周三"),
    Thursday("4", "周四"),
    Friday("5", "周五"),
    Saturday("6", "周六"),
    Sunday("7", "周日");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}
