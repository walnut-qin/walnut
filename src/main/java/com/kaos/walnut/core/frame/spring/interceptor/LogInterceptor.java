package com.kaos.walnut.core.frame.spring.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.ObjectUtils;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.log4j.Log4j2;

@Log4j2
class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 仅记录Controller的log
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 获取日志构造器
        var logBuilder = new StringBuilder();

        // 获取请求类型
        logBuilder.append(String.format(" [%s]", request.getMethod()));

        // 对注解了ApiName的方法改名
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(ApiName.class)) {
            // 提取注解内容
            ApiName apiNameAnnotation = method.getAnnotation(ApiName.class);
            logBuilder.append(String.format(" <%s>", apiNameAnnotation.value()));
        }

        // 获取请求参数
        logBuilder.append(String.format(" param = %s", ObjectUtils.serialize(request.getParameterMap())));

        // 记录日志
        log.info(logBuilder.toString());

        return true;
    }
}
