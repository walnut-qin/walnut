package com.kaos.walnut.api.data.cache;

import org.springframework.core.convert.converter.Converter;

import com.kaos.walnut.api.data.entity.DawnOrgEmpl;
import com.kaos.walnut.api.data.mapper.DawnOrgEmplMapper;
import com.kaos.walnut.core.type.Cache;

public class DawnOrgEmplCache extends Cache<String, DawnOrgEmpl> {
    /**
     * 构造函数
     * 
     * @param dawnOrgEmplMapper
     */
    public DawnOrgEmplCache(DawnOrgEmplMapper dawnOrgEmplMapper) {
        super(new Converter<String, DawnOrgEmpl>() {
            @Override
            public DawnOrgEmpl convert(String emplId) {
                return dawnOrgEmplMapper.selectById(emplId);
            }
        });
    }
}
