package com.kaos.walnut.core.util;

public final class IntegerUtils {
    /**
     * 比较两个Integer对象是否相等
     * 
     * @param int1
     * @param int2
     * @return
     */
    public static boolean equals(Integer int1, Integer int2) {
        if (int1 == int2) {
            return true;
        }
        if (int1 == null || int2 == null) {
            return false;
        }
        return int1.equals(int2);
    }

    /**
     * 比较两个Integer的大小
     * 
     * @param int1
     * @param int2
     * @param nullIsLess
     * @return
     */
    public static int compare(Integer int1, Integer int2, boolean nullIsLess) {
        if (int1 == int2) {
            return 0;
        }
        if (int1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (int2 == null) {
            return nullIsLess ? 1 : -1;
        }
        return int1.compareTo(int2);
    }

    /**
     * 比较两个数大小
     * 
     * @param int1
     * @param int2
     * @return
     */
    public static int compare(Integer int1, Integer int2) {
        return compare(int1, int2, true);
    }
}
