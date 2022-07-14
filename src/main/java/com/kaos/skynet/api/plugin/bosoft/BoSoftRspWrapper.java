package com.kaos.skynet.api.plugin.bosoft;

import com.kaos.skynet.core.util.ObjectUtils;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Data;

@Data
class BoSoftRspWrapper {
    /**
     * 拆卸包装
     * 
     * @param json
     * @return
     */
    public <T> BoSoftData<T> unwrap(RspBody result, Class<T> classOfT) {
        // 数据域解码
        String jsonStr = StringUtils.base64Decode(result.data);

        // 数据域反序列化
        Data data = ObjectUtils.deserialize(jsonStr, Data.class);

        // message域解码
        String messageStr = StringUtils.base64Decode(data.message);

        // 判断响应
        if (!data.result.equals("S0000")) {
            return new BoSoftData<>(data.result, messageStr, null);
        } else {
            return new BoSoftData<>(data.result, null, ObjectUtils.deserialize(messageStr, classOfT));
        }
    }

    static class RspBody {
        /**
         * 响应数据
         */
        String data;

        /**
         * 干扰数据
         */
        String noise;

        /**
         * 签名
         */
        String sign;
    }

    /**
     * Data域
     */
    static class Data {
        /**
         * 响应结果
         */
        String result;

        /**
         * 响应消息
         */
        String message;
    }
}
