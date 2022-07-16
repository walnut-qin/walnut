package com.kaos.walnut.core.util;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import com.google.common.base.Objects;

public final class ObjectUtils {
    /**
     * gson包装器
     */
    final static GsonWrapper gsonWrapper = new GsonWrapper();

    /**
     * 静态空对象
     */
    public final static Object EMPTY = new Object();

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

    /**
     * 序列化
     * 
     * @param object
     * @return
     */
    public static String serialize(Object object) {
        return gsonWrapper.toJson(object);
    }

    /**
     * 序列化
     * 
     * @param object
     * @return
     */
    public static void serialize(Object object, Writer writer) {
        gsonWrapper.toJson(object, writer);
    }

    /**
     * 反序列化
     * 
     * @param <T>
     * @param source
     * @param classOfT
     * @return
     */
    public static <T> T deserialize(String source, Class<T> classOfT) {
        return gsonWrapper.fromJson(source, classOfT);
    }

    /**
     * 反序列化
     * 
     * @param <T>
     * @param source
     * @param classOfT
     * @return
     */
    public static <T> T deserialize(Reader reader, Type classOfT) {
        return gsonWrapper.fromJson(reader, classOfT);
    }

    /**
     * 克隆
     * 
     * @param <T>
     * @param object
     * @param classOfT
     * @return
     */
    public static <T> T clone(T object, Class<T> classOfT) {
        String str = serialize(object);
        return deserialize(str, classOfT);
    }
}
