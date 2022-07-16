package com.kaos.walnut.api.data.his.cache.xyhis;

import com.kaos.walnut.api.data.his.entity.xyhis.ComPatientInfo;
import com.kaos.walnut.api.data.his.mapper.xyhis.ComPatientInfoMapper;
import com.kaos.walnut.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ComPatientInfoCache extends Cache<String, ComPatientInfo> {
    @Autowired
    ComPatientInfoCache(ComPatientInfoMapper comPatientInfoMapper) {
        super(new Converter<String, ComPatientInfo>() {
            @Override
            public ComPatientInfo convert(String cardNo) {
                return comPatientInfoMapper.selectById(cardNo);
            }
        });
    }
}
