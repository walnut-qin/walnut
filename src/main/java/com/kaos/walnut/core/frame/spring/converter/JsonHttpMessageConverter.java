/*********************************************************
 * File: JsonHttpMessageConverter.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  实现 json 对象类型的消息转换
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.spring.converter;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import com.kaos.walnut.core.util.ObjectUtils;

import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;

class JsonHttpMessageConverter extends AbstractJsonHttpMessageConverter {
    /**
     * 读body
     */
    @Override
    protected Object readInternal(Type resolvedType, Reader reader) throws Exception {
        return ObjectUtils.deserialize(reader, resolvedType);
    }

    /**
     * 写body
     */
    @Override
    protected void writeInternal(Object object, Type type, Writer writer) throws Exception {
        ObjectUtils.serialize(object, writer);
    }
}
