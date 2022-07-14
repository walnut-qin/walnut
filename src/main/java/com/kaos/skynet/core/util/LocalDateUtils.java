package com.kaos.skynet.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateUtils {
    /**
     * 格式化
     * 
     * @param localDateTime
     * @return
     */
    public static final String format(LocalDate localDate, String partten) {
        if (localDate == null) {
            return null;
        }

        return localDate.format(DateTimeFormatter.ofPattern(partten));
    }
}
