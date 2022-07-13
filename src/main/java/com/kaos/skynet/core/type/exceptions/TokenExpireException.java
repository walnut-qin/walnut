package com.kaos.skynet.core.type.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenExpireException extends java.lang.Exception {
    String message;
}
