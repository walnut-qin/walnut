package com.kaos.skynet.api.data.his.cache.xyhis;

import com.kaos.skynet.api.data.his.entity.xyhis.FinIprInMainInfo;
import com.kaos.skynet.api.data.his.mapper.xyhis.FinIprInMainInfoMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FinIprInMainInfoCache extends Cache<String, FinIprInMainInfo> {
    @Autowired
    FinIprInMainInfoCache(FinIprInMainInfoMapper finIprInMainInfoMapper) {
        super(new Converter<String, FinIprInMainInfo>() {
            @Override
            public FinIprInMainInfo convert(String inpatientNo) {
                return finIprInMainInfoMapper.selectById(inpatientNo);
            }
        });
    }
}
