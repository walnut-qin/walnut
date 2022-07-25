package com.kaos.walnut.core.util;

import java.time.Duration;

public final class DurationUtils {
    /**
     * 比较两个Integer对象是否相等
     * 
     * @param int1
     * @param int2
     * @return
     */
    public static boolean equals(Duration d1, Duration d2) {
        if (d1 == d2) {
            return true;
        }
        if (d1 == null || d2 == null) {
            return false;
        }
        return d1.equals(d2);
    }

    /**
     * 比较两个Integer的大小
     * 
     * @param int1
     * @param int2
     * @param nullIsLess
     * @return
     */
    public static int compare(Duration d1, Duration d2, boolean nullIsLess) {
        if (d1 == d2) {
            return 0;
        }
        if (d1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (d2 == null) {
            return nullIsLess ? 1 : -1;
        }
        return d1.compareTo(d2);
    }

    /**
     * 比较两个数大小
     * 
     * @param int1
     * @param int2
     * @return
     */
    public static int compare(Duration d1, Duration d2) {
        return compare(d1, d2, true);
    }
}
