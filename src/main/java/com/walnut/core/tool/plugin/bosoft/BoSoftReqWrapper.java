package com.walnut.core.tool.plugin.bosoft;

import java.time.LocalDateTime;

import com.google.common.collect.Lists;
import com.walnut.core.util.LocalDateTimeUtils;
import com.walnut.core.util.ObjectUtils;
import com.walnut.core.util.StringUtils;

import lombok.Builder;

class BoSoftReqWrapper {
    /**
     * 加密Key值
     */
    static final String key = "dd1893efc3e234a2d2826ad107";

    <T> ReqBody wrap(T data) {
        var builder = ReqBody.builder();

        builder.appid("XYSZXYY1551463");
        builder.data(StringUtils.base64Encode(ObjectUtils.serialize(data)));
        builder.noise(LocalDateTimeUtils.format(LocalDateTime.now(), "yyyyMMddHHmmssSSS"));
        builder.version("1.0");
        String key = String.join("&", Lists.newArrayList(
                "appid=".concat(builder.appid),
                "data=".concat(builder.data),
                "noise=".concat(builder.noise),
                "key=".concat("dd1893efc3e234a2d2826ad107"),
                "version=".concat(builder.version)));
        builder.sign(StringUtils.md5DigestAsHex(key).toUpperCase());

        return builder.build();
    }

    @Builder
    static class ReqBody {
        /**
         * 请求ID
         */
        String appid;

        /**
         * 业务数据
         */
        String data;

        /**
         * 用时间戳生成noise
         */
        String noise;

        /**
         * 版本号
         */
        String version;

        /**
         * 签名
         */
        String sign;
    }
}
