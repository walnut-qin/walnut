/*********************************************************
 * File: ResponseAdvice.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  在写body前实现springboot响应封装功能
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.spring.advice;

import com.kaos.walnut.core.frame.spring.Wrapper;
import com.kaos.walnut.core.util.StringUtils;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        // 跳出非 application/json 的响应
        if (!StringUtils.equals(selectedContentType.getSubtype(), "json")) {
            return body;
        }

        // 跳出前向异常处理过的响应
        if (body instanceof Wrapper) {
            return body;
        }

        // 跳出404error的响应
        if (request instanceof ServletServerHttpRequest) {
            var req = (ServletServerHttpRequest) request;
            if (StringUtils.equals(req.getServletRequest().getRequestURI(), "/error")) {
                return body;
            }
        }

        // 包装
        return new Wrapper(0, null, body);
    }
}
