package com.kaos.skynet.core.util;

import com.google.common.base.Objects;

public final class ObjectUtils {
    /**
     * 静态空对象
     */
    public final static Object emptyObject = new Object();

    /**
     * 比较两个Integer对象是否相等
     * 
     * @param s1
     * @param s2
     * @return
     */
    public static int hashCode(Object... objects) {
        return Objects.hashCode(objects);
    }
}
