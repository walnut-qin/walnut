package com.walnut.core.tool.plugin.timor;

import java.time.Duration;
import java.time.LocalDate;

import com.walnut.core.tool.RestTemplateWrapper;
import com.walnut.core.type.Cache;
import com.walnut.core.util.LocalDateUtils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TimorPlugin {
    /**
     * Http句柄
     */
    final RestTemplateWrapper restTemplateWrapper = new RestTemplateWrapper("timor.tech", 80);

    /**
     * 缓存
     */
    final TimorCache timorCache = new TimorCache();

    /**
     * 获取日期信息
     * 
     * @param date
     * @return
     */
    public TimorData getDayInfo(LocalDate date) {
        return timorCache.get(date);
    }

    /**
     * 缓存
     */
    @Component
    class TimorCache extends Cache<LocalDate, TimorData> {
        TimorCache() {
            super(365, new Converter<LocalDate, TimorData>() {
                @Override
                public TimorData convert(LocalDate key) {
                    // 构造请求url
                    String url = "/api/holiday/info/{date}";
                    // 从web端获取响应
                    return restTemplateWrapper.get(url, TimorData.class, LocalDateUtils.format(key, "yyyy-MM-dd"));
                };
            }, Duration.ofDays(30), Duration.ofDays(90));
        }
    }
}
