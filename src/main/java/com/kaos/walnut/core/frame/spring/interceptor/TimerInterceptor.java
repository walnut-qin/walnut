/*********************************************************
 * File: TimerInterceptor.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  在过滤器中维护计时器
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaos.walnut.core.tool.Timer;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

class TimerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 仅记录Controller的log
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Timer.create();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 仅记录Controller的log
        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        Timer.destroy();
    }
}
