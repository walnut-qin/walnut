package com.kaos.walnut.core.util;

import com.kaos.walnut.core.type.Enum;

import lombok.extern.log4j.Log4j2;

@Log4j2
public final class EnumUtils {
    /**
     * 从值域构造Enum
     * 
     * @param <T>
     * @param value
     * @param classOfT
     * @return
     */
    public static <T extends Enum> T fromValue(String value, Class<T> classOfT) {
        for (T item : classOfT.getEnumConstants()) {
            if (StringUtils.equals(item.getValue(), value)) {
                return item;
            }
        }
        String errMsg = String.format("String转Enum失败, value无对照, [%s -> %s]", value, classOfT.getName());
        log.error(errMsg);
        throw new RuntimeException(errMsg);
    }

    /**
     * 从描述域构造Enum
     * 
     * @param <T>
     * @param value
     * @param classOfT
     * @return
     */
    public static <T extends Enum> T fromDescription(String value, Class<T> classOfT) {
        for (T item : classOfT.getEnumConstants()) {
            if (StringUtils.equals(item.getDescription(), value)) {
                return item;
            }
        }
        String errMsg = String.format("String转Enum失败, description无对照, [%s -> %s]", value, classOfT.getName());
        log.error(errMsg);
        throw new RuntimeException(errMsg);
    }
}
