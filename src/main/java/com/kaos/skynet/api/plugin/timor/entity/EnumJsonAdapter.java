package com.kaos.skynet.api.plugin.timor.entity;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.EnumUtils;

class EnumJsonAdapter<E extends Enum> implements JsonSerializer<E>, JsonDeserializer<E> {
    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        if (src != null) {
            return new JsonPrimitive(src.getValue());
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws com.google.gson.JsonParseException {
        // 执行转换
        return EnumUtils.fromValue(json.getAsString(), (Class<E>) typeOfT);
    }
}
