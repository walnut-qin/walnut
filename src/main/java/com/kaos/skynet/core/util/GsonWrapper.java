package com.kaos.skynet.core.util;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.Cache;
import com.kaos.skynet.core.type.Enum;

import lombok.extern.log4j.Log4j2;

class GsonWrapper {
    /**
     * Gson原始对象
     */
    Gson gson;

    /**
     * 默认构造
     */
    public GsonWrapper() {
        // 创建构造器
        var builder = new GsonBuilder();

        // 添加默认适配器
        builder.registerTypeHierarchyAdapter(Enum.class, new Adapter.EnumTypeAdapter<>());
        builder.registerTypeAdapter(Date.class, new Adapter.DateTypeAdapter());
        builder.registerTypeAdapter(LocalDateTime.class, new Adapter.LocalDateTimeTypeAdapter());
        builder.registerTypeAdapter(LocalDate.class, new Adapter.LocalDateTypeAdapter());
        builder.registerTypeAdapter(LocalTime.class, new Adapter.LocalTimeTypeAdapter());
        builder.registerTypeAdapter(Period.class, new Adapter.PeriodTypeAdapter());
        builder.registerTypeHierarchyAdapter(Cache.class, new Adapter.CacheTypeAdapter<>());

        // 构造gson对象
        gson = builder.create();
    }

    /**
     * 序列化
     * 
     * @param src 数据源
     * @return
     */
    public String toJson(Object src) {
        return gson.toJson(src);
    }

    /**
     * 序列化
     * 
     * @param src    数据源
     * @param writer 写入器
     */
    public void toJson(Object src, Writer writer) {
        gson.toJson(src, writer);
    }

    /**
     * 反序列化
     * 
     * @param <T>  反序列化目标类型
     * @param json 数据源读取器
     * @param type 目标类型
     * @return
     */
    public <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    /**
     * 用于反射的反序列化
     * 
     * @param <T>    反序列化目标类型
     * @param reader 数据源读取器
     * @param type   目标类型
     * @return
     */
    public <T> T fromJson(Reader reader, Type type) {
        return gson.fromJson(reader, type);
    }

    /**
     * 格式化json字符串
     */
    public String format(String json) {
        var jsonElement = JsonParser.parseString(json);
        return gson.toJson(jsonElement);
    }

    /**
     * 深拷贝
     * 
     * @param <T>  目标类型
     * @param src  拷贝源
     * @param type 目标类型
     * @return
     */
    public <T> T clone(T src, Type type) {
        // 已正反序列化
        return this.fromJson(this.toJson(src), type);
    }

    static class Adapter {
        /**
         * 枚举类型适配器
         */
        @Log4j2
        static class EnumTypeAdapter<E extends Enum> implements JsonSerializer<E>, JsonDeserializer<E> {
            @Override
            public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
                if (src != null) {
                    return new JsonPrimitive(src.getDescription());
                }
                return null;
            }

            @Override
            @SuppressWarnings("unchecked")
            public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws com.google.gson.JsonParseException {
                Class<E> classOfE = (Class<E>) typeOfT;
                for (var item : classOfE.getEnumConstants()) {
                    if (StringUtils.equals(item.getDescription(), json.getAsString())) {
                        return item;
                    }
                }
                String err = String.format("反序列化[%s]失败, 值[%s]", classOfE.getName(), json.getAsString());
                log.warn(err);
                throw new RuntimeException(err);
            }
        }

        /**
         * 日期适配器
         */
        @Log4j2
        static class DateTypeAdapter extends TypeAdapter<Date> {
            /**
             * 标准格式
             */
            final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            @Override
            public Date read(JsonReader in) throws IOException {
                if (in.peek() == null) {
                    return null;
                } else {
                    String value = in.nextString();
                    try {
                        return formatter.parse(value);
                    } catch (Exception e) {
                        String err = String.format("反序列化[Date]失败, 值[%s]", value);
                        log.error(err);
                        throw new RuntimeException(err);
                    }
                }
            }

            @Override
            public void write(JsonWriter out, Date value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(formatter.format(value));
                }
            }
        }

        /**
         * LocalDateTime适配器
         */
        static class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {
            /**
             * 字符串格式
             */
            final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            @Override
            public LocalDateTime read(JsonReader in) throws IOException {
                if (in.peek() == null) {
                    return null;
                } else {
                    return LocalDateTime.parse(in.nextString(), formatter);
                }
            }

            @Override
            public void write(JsonWriter out, LocalDateTime value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value.format(formatter));
                }
            }
        }

        /**
         * LocalDate适配器
         */
        static class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {
            /**
             * 字符串格式
             */
            final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            @Override
            public LocalDate read(JsonReader in) throws IOException {
                if (in.peek() == null) {
                    return null;
                } else {
                    return LocalDate.parse(in.nextString(), formatter);
                }
            }

            @Override
            public void write(JsonWriter out, LocalDate value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value.format(formatter));
                }
            }
        }

        /**
         * LocalTime适配器
         */
        static class LocalTimeTypeAdapter extends TypeAdapter<LocalTime> {
            /**
             * 字符串格式
             */
            final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            @Override
            public LocalTime read(JsonReader in) throws IOException {
                if (in.peek() == null) {
                    return null;
                } else {
                    return LocalTime.parse(in.nextString(), formatter);
                }
            }

            @Override
            public void write(JsonWriter out, LocalTime value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value.format(formatter));
                }
            }
        }

        /**
         * Period适配器 - 年龄
         */
        @Log4j2
        static class PeriodTypeAdapter extends TypeAdapter<Period> {
            @Override
            public Period read(JsonReader in) throws IOException {
                log.error("Period不支持反序列化");
                throw new RuntimeException("Period不支持反序列化");
            }

            @Override
            public void write(JsonWriter out, Period value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(periodToString(value));
                }
            }

            private String periodToString(Period source) {
                // 构造结果串
                String retStr = "";

                // 年
                if (source.getYears() != 0) {
                    retStr += String.format("%d%s", source.getYears(), "岁");
                }

                // 月
                if (source.getMonths() != 0) {
                    retStr += String.format("%d%s", source.getMonths(), "月");
                }

                // 日
                if (source.getDays() != 0) {
                    retStr += String.format("%d%s", source.getDays(), "天");
                }

                return retStr;
            }
        }

        /**
         * 缓存适配器
         */
        static class CacheTypeAdapter<K, V> extends TypeAdapter<Cache<K, V>> {
            @Override
            public Cache<K, V> read(JsonReader in) throws IOException {
                return null;
            }

            @Override
            public void write(JsonWriter out, Cache<K, V> value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    // 获取统计值
                    var stats = value.getStats();
                    // 序列化
                    out.beginObject();
                    out.name("hitCount");
                    out.value(stats.hitCount());
                    out.name("missCount");
                    out.value(stats.missCount());
                    out.name("loadSuccessCount");
                    out.value(stats.loadSuccessCount());
                    out.name("loadExceptionCount");
                    out.value(stats.loadExceptionCount());
                    out.name("evictionCount");
                    out.value(stats.evictionCount());
                    out.endObject();
                }
            }
        }
    }
}
