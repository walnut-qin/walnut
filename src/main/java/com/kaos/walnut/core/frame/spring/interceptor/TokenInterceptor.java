package com.kaos.walnut.core.frame.spring.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaos.walnut.core.api.data.entity.KaosUser;
import com.kaos.walnut.core.api.logic.service.TokenService;
import com.kaos.walnut.core.type.annotations.PassToken;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.log4j.Log4j2;

@Log4j2
class TokenInterceptor implements HandlerInterceptor {
    /**
     * 核心数据库 - 账户信息接口
     */
    TokenService tokenService;

    public TokenInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 判断Pass注解 - 临近原则
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.value()) {
                return true;
            }
        } else if (method.getDeclaringClass().isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getDeclaringClass().getAnnotation(PassToken.class);
            if (passToken.value()) {
                return true;
            }
        }

        // 校验token
        KaosUser kaosUser = tokenService.checkToken(request.getHeader("Token"), response);

        // 记录用户
        KaosUser.create(kaosUser);

        // 记录日志
        var logBuilder = new StringBuilder();
        logBuilder.append(String.format("loginUser = [%s]", kaosUser.getUserName()));
        log.info(logBuilder.toString());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 仅记录Controller的log
        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        KaosUser.destroy();
    }
}
