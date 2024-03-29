/*********************************************************
 * File: HttpMessageConverterConfigurer.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  登记 HTTP 接口的消息类型转换器
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.spring.converter;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class HttpMessageConverterConfigurer implements WebMvcConfigurer {
    /**
     * 注册HttpMessageConverter，用于读写Http消息的body
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 注册处理器
        converters.add(0, new JsonHttpMessageConverter());
        converters.add(0, new WorkbookHttpMessageConverter());
        converters.add(0, new BufferedImageHttpMessageConverter());

        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
}
