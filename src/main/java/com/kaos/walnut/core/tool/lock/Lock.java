package com.kaos.walnut.core.tool.lock;

import java.util.List;

import com.google.common.collect.Lists;

public final class Lock {
    /**
     * 锁名
     */
    String name;

    /**
     * 锁芯
     */
    List<Object> objects;

    /**
     * 构造函数
     * 
     * @param name
     * @param size
     */
    public Lock(String name, Integer size) {
        this.name = name;
        this.objects = Lists.newArrayListWithCapacity(size);
        for (int i = 0; i < size; i++) {
            this.objects.add(new Object());
        }
    }
}
