package com.walnut.core.tool.plugin.timor;

import java.time.LocalDate;

import com.google.gson.annotations.JsonAdapter;
import com.walnut.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TimorData {
    /**
     * 服务状态
     */
    @JsonAdapter(EnumJsonAdapter.class)
    private ResultCodeEnum code;

    /**
     * Type 实体
     */
    private Type type;

    /**
     * Type 定义
     */
    @Getter
    public class Type {
        /**
         * 日期类型
         */
        @JsonAdapter(EnumJsonAdapter.class)
        private DayTypeEnum type;

        /**
         * 类型中文名
         */
        private String name;

        /**
         * 星期
         */
        @JsonAdapter(EnumJsonAdapter.class)
        private WeekEnum week;
    }

    /**
     * 节假日实体
     */
    private Holiday holiday;

    /**
     * Holiday 定义
     */
    @Getter
    public class Holiday {
        /**
         * 是否为节假日标识
         */
        private Boolean holiday;

        /**
         * 节日名称
         */
        private String name;

        /**
         * 薪资倍数
         */
        private Integer wage;

        /**
         * 调休时有该字段，表示哪个节日的调休
         */
        private String target;

        /**
         * 调休下有该字段，true表示放完假再调休，false表示先调休再放假
         */
        private Boolean after;

        /**
         * 日期
         */
        private LocalDate date;

        /**
         * 剩余日期
         */
        private Integer rest;
    }

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
}
