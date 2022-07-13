package com.kaos.skynet.core.frame.spring.formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

import lombok.extern.log4j.Log4j2;

@Log4j2
class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    /**
     * 字符串格式
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime convert(String source) {
        try {
            // 判空
            if (source == null) {
                return null;
            }

            // 格式化
            return LocalDateTime.parse(source, formatter);
        } catch (Exception e) {
            String err = String.format("String转LocalDateTime失败, org = %s", source);
            log.error(err);
            throw new RuntimeException(err);
        }
    }
}
