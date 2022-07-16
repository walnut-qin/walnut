package com.kaos.walnut.api.data.his.cache.xyhis;

import com.kaos.walnut.api.data.his.entity.xyhis.FinIprInMainInfo;
import com.kaos.walnut.api.data.his.mapper.xyhis.FinIprInMainInfoMapper;
import com.kaos.walnut.core.type.Cache;

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
