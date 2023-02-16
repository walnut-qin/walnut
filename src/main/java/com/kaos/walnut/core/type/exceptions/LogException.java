package com.kaos.walnut.core.type.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 附带日志信息的异常类
 */
@Getter
@AllArgsConstructor
public class LogException extends java.lang.Exception {
    /**
     * 日志信息
     */
    Object objMsg;
}
