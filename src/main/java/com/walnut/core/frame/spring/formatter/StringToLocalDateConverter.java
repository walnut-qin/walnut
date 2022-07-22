package com.walnut.core.frame.spring.formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

import lombok.extern.log4j.Log4j2;

@Log4j2
class StringToLocalDateConverter implements Converter<String, LocalDate> {
    /**
     * 字符串格式
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate convert(String source) {
        try {
            // 判空
            if (source == null) {
                return null;
            }

            // 格式化
            return LocalDate.parse(source, formatter);
        } catch (Exception e) {
            String err = String.format("String转LocalDate失败, org = %s", source);
            log.error(err);
            throw new RuntimeException(err);
        }
    }
}
