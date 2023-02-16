/*********************************************************
 * File: LogInterceptor.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  在过滤器中自动记录请求日志
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.spring.interceptor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaos.walnut.core.frame.entity.User;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import lombok.extern.log4j.Log4j2;

@Log4j2
class LogInterceptor implements HandlerInterceptor {
    /**
     * 日志构造器
     */
    static ThreadLocal<StringBuilder> logBuilder = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 仅记录Controller的log
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 获取日志构造器
        logBuilder.set(new StringBuilder());

        // 用户信息
        User currentUser = User.currentUser();
        if (currentUser != null) {
            logBuilder.get().append(String.format(" [%s]", currentUser.getName()));
        }

        // 对注解了ApiName的方法改名
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(ApiName.class)) {
            // 提取注解内容
            ApiName apiNameAnnotation = method.getAnnotation(ApiName.class);
            logBuilder.get().append(String.format(" <%s>", apiNameAnnotation.value()));
        }

        // 获取请求类型
        String requestMethod = request.getMethod();
        logBuilder.get().append(String.format(" [%s]", requestMethod));

        // GET请求记录参数
        if (StringUtils.equals(requestMethod, "GET")) {
            logBuilder.get().append(String.format(" param = %s", ObjectUtils.serialize(request.getParameterMap())));
        }

        // 若含有RequestBody, 延迟到Advice中记录日志
        var parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] annotations : parameterAnnotations) {
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().isAssignableFrom(RequestBody.class)) {
                    return true;
                }
            }
        }

        // 记录日志
        log.info(logBuilder.get().toString());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logBuilder.remove();
    }

    /**
     * 内置一个Advice用于处理带Body的日志
     */
    @ControllerAdvice
    static class LogAdvice implements RequestBodyAdvice {
        @Override
        public boolean supports(MethodParameter methodParameter, Type targetType,
                Class<? extends HttpMessageConverter<?>> converterType) {
            return true;
        }

        @Override
        public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
            return inputMessage;
        }

        @Override
        public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
            // 加入body
            if (body instanceof Workbook) {
                logBuilder.get().append(String.format(" body = %s", "workbook"));
            } else {
                logBuilder.get().append(String.format(" body = %s", ObjectUtils.serialize(body)));
            }

            // 写日志
            log.info(logBuilder.get().toString());

            // 透传body
            return body;
        }

        @Override
        public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
            return body;
        }
    }
}
