package com.walnut.core.frame.spring.converter;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import com.walnut.core.util.ObjectUtils;

import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;

class JsonHttpMessageConverter extends AbstractJsonHttpMessageConverter {
    @Override
    protected Object readInternal(Type resolvedType, Reader reader) throws Exception {
        return ObjectUtils.deserialize(reader, resolvedType);
    }

    @Override
    protected void writeInternal(Object object, Type type, Writer writer) throws Exception {
        ObjectUtils.serialize(object, writer);
    }
}
