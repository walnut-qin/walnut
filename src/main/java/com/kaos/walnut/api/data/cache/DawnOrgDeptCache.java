package com.kaos.walnut.api.data.cache;

import org.springframework.core.convert.converter.Converter;

import com.kaos.walnut.api.data.entity.DawnOrgDept;
import com.kaos.walnut.api.data.mapper.DawnOrgDeptMapper;
import com.kaos.walnut.core.type.Cache;

public class DawnOrgDeptCache extends Cache<String, DawnOrgDept> {
    /**
     * 构造函数
     * 
     * @param dawnOrgDeptMapper
     */
    public DawnOrgDeptCache(DawnOrgDeptMapper dawnOrgDeptMapper) {
        super(new Converter<String, DawnOrgDept>() {
            @Override
            public DawnOrgDept convert(String deptId) {
                return dawnOrgDeptMapper.selectById(deptId);
            }
        });
    }
}
