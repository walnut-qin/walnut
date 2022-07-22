package com.walnut.core.frame.spring.advice;

import com.walnut.core.util.StringUtils;

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
        // 仅包装application/json的类型
        if (!StringUtils.equals(selectedContentType.getSubtype(), "json")) {
            return body;
        }

        // 异常处理，不包装
        if (body instanceof Wrapper) {
            return body;
        }

        // 特殊响应不处理，例如404
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
