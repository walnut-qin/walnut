/*********************************************************
 * File: FormatterConfigurer.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  登记 HTTP 请求的 URI 参数转换器
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.spring.formatter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * URL参数格式化处理器
 */
@Configuration
class FormatterConfigurer implements WebMvcConfigurer {
    /**
     * 参数格式化工具
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 注册枚举解析器工厂
        registry.addConverterFactory(new StringToEnumConverterFactory());

        // 注册时间解析
        registry.addConverter(new StringToDateConverter());
        registry.addConverter(new StringToLocalDateTimeConverter());
        registry.addConverter(new StringToLocalDateConverter());
        registry.addConverter(new StringToLocalTimeConverter());

        WebMvcConfigurer.super.addFormatters(registry);
    }
}
