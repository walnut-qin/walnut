package com.kaos.walnut.core.util;

import java.util.List;

import org.springframework.core.convert.converter.Converter;

public final class ListUtils {
    /**
     * 按照条件分发列表
     * 
     * @param <T>
     * @param org       原始列表
     * @param cond      条件
     * @param trueList  满足条件的列表
     * @param falseList 不满足条件的列表
     */
    public static <T> void distribute(List<T> org, Converter<T, Boolean> cond, List<T> trueList, List<T> falseList) {
        for (int i = 0; i < org.size(); i++) {
            T item = org.get(i);
            if (cond.convert(item)) {
                trueList.add(item);
            } else {
                falseList.add(item);
            }
        }
    }
}
