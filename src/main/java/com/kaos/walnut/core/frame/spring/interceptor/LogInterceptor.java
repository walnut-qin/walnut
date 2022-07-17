package com.kaos.walnut.core.frame.spring.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaos.walnut.core.api.data.entity.KaosUser;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogInterceptor implements HandlerInterceptor {
    /**
     * 日志构造器
     */
    public static ThreadLocal<StringBuilder> logBuilder = new ThreadLocal<>();

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
        logBuilder.get().append(String.format(" [%s]", KaosUser.getCurrentUser().getUserName()));

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
}
