package com.kaos.skynet.core.frame.module.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaos.skynet.core.util.ObjectUtils;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.log4j.Log4j2;

@Log4j2
class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取日志构造器
        var logBuilder = new StringBuilder();

        // 获取请求类型
        logBuilder.append(String.format(" [%s]", request.getMethod()));

        // 获取请求参数
        logBuilder.append(String.format(" param = %s", ObjectUtils.serialize(request.getParameterMap())));

        // 记录日志
        log.info(logBuilder.toString());

        return true;
    }
}
