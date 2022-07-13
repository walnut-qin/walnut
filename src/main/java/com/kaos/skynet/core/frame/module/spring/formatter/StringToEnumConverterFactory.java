package com.kaos.skynet.core.frame.module.spring.formatter;

import com.kaos.skynet.core.type.Enum;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import lombok.extern.log4j.Log4j2;

@Log4j2
class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {
    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new Converter<String, T>() {
            @Override
            public T convert(String source) {
                // 判空
                if (source == null) {
                    return null;
                }

                // 轮训检查
                for (T e : targetType.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }

                String err = String.format("String转Enum失败, org = %s, target = %s", source, targetType.getName());
                log.error(err);
                throw new RuntimeException(err);
            }
        };
    }
}
