/*********************************************************
 * File: StringToDateConverter.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  实现字符串到 Date 的转换
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.spring.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import lombok.extern.log4j.Log4j2;

@Log4j2
class StringToDateConverter implements Converter<String, Date> {
    final static private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date convert(String source) {
        try {
            return formatter.parse(source);
        } catch (Exception e) {
            String err = String.format("String转Date失败, org = %s", source);
            log.error(err);
            throw new RuntimeException(err);
        }
    }
}
