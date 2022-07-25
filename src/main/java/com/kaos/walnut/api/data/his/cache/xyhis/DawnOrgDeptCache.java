package com.kaos.walnut.api.data.his.cache.xyhis;

import com.kaos.walnut.api.data.his.entity.xyhis.DawnOrgDept;
import com.kaos.walnut.api.data.his.mapper.xyhis.DawnOrgDeptMapper;
import com.kaos.walnut.core.type.Cache;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DawnOrgDeptCache extends Cache<String, DawnOrgDept> {
    DawnOrgDeptCache(DawnOrgDeptMapper dawnOrgDeptMapper) {
        super(new Converter<String, DawnOrgDept>() {
            @Override
            public DawnOrgDept convert(String deptCode) {
                return dawnOrgDeptMapper.selectById(deptCode);
            }
        });
    }
}
