/*********************************************************
 * File: StringToDateConverter.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  实现字符串到 Enum 的转换
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

 package com.kaos.walnut.core.frame.spring.formatter;

import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.util.EnumUtils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {
    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new Converter<String, T>() {
            @Override
            public T convert(String source) {
                return EnumUtils.fromDescription(source, targetType);
            }
        };
    }
}
