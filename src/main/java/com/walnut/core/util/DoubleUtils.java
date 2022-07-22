package com.walnut.core.util;

public final class DoubleUtils {
    /**
     * 比较两个Double对象是否相等
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static boolean equals(Double d1, Double d2) {
        if (d1 == d2) {
            return true;
        }
        if (d1 == null || d2 == null) {
            return false;
        }
        return d1.equals(d2);
    }

    /**
     * 比较两个Double的大小
     * 
     * @param d1
     * @param d2
     * @param nullIsLess
     * @return
     */
    public static int compare(Double d1, Double d2, boolean nullIsLess) {
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
     * @param d1
     * @param d2
     * @return
     */
    public static int compare(Double d1, Double d2) {
        return compare(d1, d2, true);
    }

    /**
     * 比较两个数大小
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Double eraseNull(Double d) {
        return d == null ? 0d : d;
    }
}
