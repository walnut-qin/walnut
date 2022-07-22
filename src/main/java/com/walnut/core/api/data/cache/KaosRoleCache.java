package com.walnut.core.api.data.cache;

import com.walnut.core.api.data.entity.KaosRole;
import com.walnut.core.api.data.mapper.KaosRoleMapper;
import com.walnut.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class KaosRoleCache extends Cache<String, KaosRole> {
    @Autowired
    public KaosRoleCache(KaosRoleMapper kaosRoleMapper) {
        super(new Converter<String, KaosRole>() {
            @Override
            public KaosRole convert(String userCode) {
                return kaosRoleMapper.selectById(userCode);
            }
        });
    }
}
