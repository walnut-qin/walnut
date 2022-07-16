package com.kaos.walnut.api.plugin.timor;

import java.time.Duration;
import java.time.LocalDate;

import com.kaos.walnut.api.plugin.timor.entity.DayInfo;
import com.kaos.walnut.core.tool.RestTemplateWrapper;
import com.kaos.walnut.core.type.Cache;
import com.kaos.walnut.core.util.LocalDateUtils;

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
