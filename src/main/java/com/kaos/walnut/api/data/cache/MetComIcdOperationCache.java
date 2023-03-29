package com.kaos.walnut.api.data.cache;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.kaos.walnut.api.data.entity.MetComIcdOperation;
import com.kaos.walnut.api.data.mapper.MetComIcdOperationMapper;
import com.kaos.walnut.core.type.Cache;

@Component
public class MetComIcdOperationCache extends Cache<String, MetComIcdOperation> {
    /**
     * 构造函数
     * 
     * @param dawnOrgEmplMapper
     */
    public MetComIcdOperationCache(MetComIcdOperationMapper metComIcdOperationMapper) {
        super(new Converter<String, MetComIcdOperation>() {
            @Override
            public MetComIcdOperation convert(String icdCode) {
                return metComIcdOperationMapper.selectById(icdCode);
            }
        });
    }
}
