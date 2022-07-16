package com.kaos.walnut.core.api.data.cache;

import com.kaos.walnut.core.api.data.entity.KaosEternalToken;
import com.kaos.walnut.core.api.data.mapper.KaosEternalTokenMapper;
import com.kaos.walnut.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class KaosEternalTokenCache extends Cache<String, KaosEternalToken> {
    @Autowired
    KaosEternalTokenCache(KaosEternalTokenMapper kaosEternalTokenMapper) {
        super(new Converter<String, KaosEternalToken>() {
            @Override
            public KaosEternalToken convert(String eternalToken) {
                return kaosEternalTokenMapper.selectById(eternalToken);
            }
        });
    }
}
