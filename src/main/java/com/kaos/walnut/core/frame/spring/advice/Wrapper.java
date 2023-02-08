/*********************************************************
 * File: Wrapper.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  响应封装类
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.spring.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Wrapper {
    /**
     * 响应码
     */
    private Integer code;

    /**
     * 错误信息 - 当响应失败时存储错误原因
     */
    private String message;

    /**
     * 响应数据 - 当响应成功时存储响应结果
     */
    private Object data;
}
