package com.walnut.core.api.data.cache;

import com.walnut.core.api.data.entity.KaosUser;
import com.walnut.core.api.data.mapper.KaosUserMapper;
import com.walnut.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class KaosUserCache extends Cache<String, KaosUser> {
    @Autowired
    public KaosUserCache(KaosUserMapper kaosUserMapper) {
        super(new Converter<String, KaosUser>() {
            @Override
            public KaosUser convert(String userCode) {
                return kaosUserMapper.selectById(userCode);
            }
        });
    }
}
