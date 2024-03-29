/*********************************************************
 * File: StringToDateConverter.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  实现字符串到 LocalTime 的转换
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

 package com.kaos.walnut.core.frame.spring.formatter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

import lombok.extern.log4j.Log4j2;

@Log4j2
class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    /**
     * 字符串格式
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalTime convert(String source) {
        try {
            // 判空
            if (source == null) {
                return null;
            }

            // 格式化
            return LocalTime.parse(source, formatter);
        } catch (Exception e) {
            String err = String.format("String转LocalTime失败, org = %s", source);
            log.error(err);
            throw new RuntimeException(err);
        }
    }
}
