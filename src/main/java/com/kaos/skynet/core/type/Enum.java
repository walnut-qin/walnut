package com.kaos.skynet.core.type;

/**
 * 定制枚举接口
 */
public interface Enum {
    /**
     * 写入数据库的值
     * 
     * @return
     */
    String getValue();

    /**
     * 序列化入json的值
     * 
     * @return
     */
    String getDescription();
}
