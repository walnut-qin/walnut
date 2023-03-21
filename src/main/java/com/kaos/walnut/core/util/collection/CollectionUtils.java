package com.kaos.walnut.core.util.collection;

import java.util.Collection;

import org.springframework.core.convert.converter.Converter;

public final class CollectionUtils {
    /**
     * 按照条件分发列表
     * 
     * @param <T>
     * @param o   原始列表
     * @param c   条件
     * @param t   满足条件的列表
     * @param f   不满足条件的列表
     */
    public static <T> void distribute(Collection<T> o, Converter<T, Boolean> c, Collection<T> t, Collection<T> f) {
        o.forEach(x -> {
            if (c.convert(x)) {
                t.add(x);
            } else {
                f.add(x);
            }
        });
    }

    /**
     * 求两个容器的并集
     * 
     * @param <T>
     * @param left
     * @param right
     * @return
     */
    public static <T> Collection<T> union(Collection<T> left, Collection<T> right) {
        return org.apache.commons.collections4.CollectionUtils.union(left, right);
    }

    /**
     * 求两个容器的交集
     * 
     * @param <T>
     * @param left
     * @param right
     * @return
     */
    public static <T> Collection<T> intersection(Collection<T> left, Collection<T> right) {
        return org.apache.commons.collections4.CollectionUtils.intersection(left, right);
    }

    /**
     * 求两个容器的交集的补集
     * 
     * @param <T>
     * @param left
     * @param right
     * @return
     */
    public static <T> Collection<T> disjunction(Collection<T> left, Collection<T> right) {
        return org.apache.commons.collections4.CollectionUtils.disjunction(left, right);
    }

    /**
     * 求两个容器的差集
     * 
     * @param <T>
     * @param left
     * @param right
     * @return
     */
    public static <T> Collection<T> subtract(Collection<T> left, Collection<T> right) {
        return org.apache.commons.collections4.CollectionUtils.subtract(left, right);
    }
}
