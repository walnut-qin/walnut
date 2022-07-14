package com.kaos.skynet.api.data.his.cache.xyhis;

import com.kaos.skynet.api.data.his.entity.xyhis.DawnOrgEmpl;
import com.kaos.skynet.api.data.his.mapper.xyhis.DawnOrgEmplMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DawnOrgEmplCache extends Cache<String, DawnOrgEmpl> {
    DawnOrgEmplCache(DawnOrgEmplMapper dawnOrgEmplMapper) {
        super(new Converter<String, DawnOrgEmpl>() {
            @Override
            public DawnOrgEmpl convert(String emplCode) {
                return dawnOrgEmplMapper.selectById(emplCode);
            }
        });
    }
}
