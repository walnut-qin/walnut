/*********************************************************
 * File: LockInterceptor.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  在过滤器阶段完成锁功能初始化
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

 package com.kaos.walnut.core.frame.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaos.walnut.core.tool.lock.LockExecutor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

class LockInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 仅记录Controller的log
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        LockExecutor.create();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 仅记录Controller的log
        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        LockExecutor.destroy();
    }
}
