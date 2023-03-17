/*********************************************************
 * File: TokenInterceptor.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  在过滤器中维护token的相关注解
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.spring.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaos.walnut.core.frame.entity.User;
import com.kaos.walnut.core.tool.RestTemplateWrapper;
import com.kaos.walnut.core.type.annotations.PassToken;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
class TokenInterceptor implements HandlerInterceptor {
    /**
     * 辅助器
     */
    private final static Helper helper = new Helper();

    /**
     * 预处理：token校验，构造user对象
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 判断Pass注解 - 临近原则<先method，再class>
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

        // 记录用户
        User.create(helper.checkToken(request, response));

        return true;
    }

    /**
     * 后处理：销毁user对象
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 仅记录Controller的log
        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        User.destroy();
    }

    /**
     * Helper
     */
    static class Helper {
        /**
         * 处理器
         */
        private RestTemplateWrapper restTemplateWrapper = new RestTemplateWrapper("walnut.auth.net", 8080);

        /**
         * 校验token
         * 
         * @param request
         * @param response
         * @return
         */
        public User checkToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
            // 获取token
            String token = request.getHeader("W-Token");
            if (token == null) {
                log.error("token校验失败: token为空");
                throw new RuntimeException("无token, 请登录");
            }

            // 构造请求
            var reqBodyBuilder = ReqBody.builder();
            reqBodyBuilder.token(token);

            // 发送校验请求
            var rspBody = restTemplateWrapper.post("/api/token/check", reqBodyBuilder.build(), RspBody.class);
            if (rspBody.getCode() != 0) {
                throw new RuntimeException(rspBody.getMessage());
            }

            return rspBody.getData().getUser();
        }

        @Data
        @Builder
        static class ReqBody {
            /**
             * 待校验token
             */
            String token;
        }

        @Getter
        static class RspBody {
            /**
             * 响应码
             */
            Integer code;

            /**
             * 错误信息 - 当响应失败时存储错误原因
             */
            String message;

            /**
             * 响应数据 - 当响应成功时存储登陆用户
             */
            Data data;

            static class Data {
                /**
                 * 登陆用户
                 */
                @Getter
                User user;
            }
        }
    }
}
