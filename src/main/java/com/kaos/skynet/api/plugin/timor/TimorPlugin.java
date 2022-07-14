package com.kaos.skynet.api.plugin.timor;

import java.time.Duration;
import java.time.LocalDate;

import com.kaos.skynet.api.plugin.timor.entity.DayInfo;
import com.kaos.skynet.core.tool.RestTemplateWrapper;
import com.kaos.skynet.core.type.Cache;
import com.kaos.skynet.core.util.LocalDateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TimorPlugin {
    /**
     * Http句柄
     */
    RestTemplateWrapper restTemplateWrapper = new RestTemplateWrapper("timor.tech", 80);

    /**
     * 缓存
     */
    @Autowired
    TimorCache timorCache;

    /**
     * 获取日期信息
     * 
     * @param date
     * @return
     */
    public DayInfo getDayInfo(LocalDate date) {
        return timorCache.get(date);
    }

    /**
     * 缓存
     */
    @Component
    class TimorCache extends Cache<LocalDate, DayInfo> {
        TimorCache() {
            super(365, new Converter<LocalDate, DayInfo>() {
                @Override
                public DayInfo convert(LocalDate key) {
                    // 构造请求url
                    String url = "/api/holiday/info/{date}";
                    // 从web端获取响应
                    return restTemplateWrapper.get(url, DayInfo.class, LocalDateUtils.format(key, "yyyy-MM-dd"));
                };
            }, Duration.ofDays(30), Duration.ofDays(90));
        }
    }
}
