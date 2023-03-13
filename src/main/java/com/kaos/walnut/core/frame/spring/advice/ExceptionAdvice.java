/*********************************************************
 * File: ExceptionAdvice.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  实现springboot框架对各种异常的切片处理
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.spring.advice;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import com.kaos.walnut.core.type.exceptions.TokenExpireException;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
class ExceptionAdvice {
    /**
     * 通常异常处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Wrapper exceptionHandler(Exception ex) {
        log.error(ex.getMessage());
        return new Wrapper(-1, ex.getMessage(), null);
    }

    /**
     * 函数参数校验失败异常处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Wrapper exceptionHandler(MethodArgumentNotValidException ex) {
        // 获取错误内容
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        var errMsg = allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));
        log.error(errMsg);
        return new Wrapper(-1, errMsg, null);
    }

    /**
     * class注解的校验异常处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Wrapper exceptionHandler(ConstraintViolationException ex) {
        var errMsg = ex.getConstraintViolations().stream().map(x -> x.getMessage()).collect(Collectors.joining(";"));
        log.error(errMsg);
        return new Wrapper(-1, errMsg, null);
    }

    /**
     * class注解的校验异常处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = TokenExpireException.class)
    public Wrapper exceptionHandler(TokenExpireException ex) {
        var errMsg = "token已过期";
        log.error(errMsg);
        return new Wrapper(-2, errMsg, null);
    }
}
