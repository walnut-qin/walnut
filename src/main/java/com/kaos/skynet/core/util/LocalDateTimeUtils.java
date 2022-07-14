package com.kaos.skynet.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LocalDateTimeUtils {
    /**
     * 格式化
     * 
     * @param localDateTime
     * @return
     */
    public static final String format(LocalDateTime localDateTime, String partten) {
        if (localDateTime == null) {
            return null;
        }

        return localDateTime.format(DateTimeFormatter.ofPattern(partten));
    }
}
