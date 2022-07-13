package com.kaos.skynet.core.frame.spring.advice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
class ExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Wrapper exceptionHandler(Exception ex) {
        return new Wrapper(-1, ex.getMessage(), null);
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Wrapper methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        // 获取错误内容
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        var errMsg = allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));
        log.error(errMsg);
        return new Wrapper(-1, errMsg, null);
    }
}
