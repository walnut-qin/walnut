/*********************************************************
 * File: KaosUserCache.java
 * Created Date: 2023-03-17
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  定义用户缓存，避免频繁查询数据库
 *  特性：
 *      刷新时间：8min
 *      过期时间：30min
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.api.data.cache;

import java.time.Duration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.kaos.walnut.api.data.entity.KaosUser;
import com.kaos.walnut.api.data.mapper.KaosUserMapper;
import com.kaos.walnut.core.type.Cache;

@Component
public class KaosUserCache extends Cache<String, KaosUser> {
    public KaosUserCache(KaosUserMapper kaosUserMapper) {
        super(200, new Converter<String, KaosUser>() {
            @Override
            public KaosUser convert(String source) {
                return kaosUserMapper.selectById(source);
            }
        }, Duration.ofMinutes(8), Duration.ofMinutes(30));
    }
}
