package com.kaos.walnut.core.type;

public interface Risu<S, T> {
    /**
     * 执行
     * 
     * @param source
     * @return
     */
    T run(S source);
}
