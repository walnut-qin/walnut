/*********************************************************
 * File: InterceptorConfigurer.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  登记springboot过滤器
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.spring.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class InterceptorConfigurer implements WebMvcConfigurer {
    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/api/**");
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/api/**");
        registry.addInterceptor(new TimerInterceptor()).addPathPatterns("/api/**");
        registry.addInterceptor(new LockInterceptor()).addPathPatterns("/api/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
