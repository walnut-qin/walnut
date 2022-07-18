package com.kaos.walnut.api.data.his.cache.kaos;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kaos.walnut.api.data.his.entity.kaos.SurgeryDeptPriv;
import com.kaos.walnut.api.data.his.mapper.kaos.SurgeryDeptPrivMapper;
import com.kaos.walnut.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SurgeryDeptPrivCache extends Cache<String, List<SurgeryDeptPriv>> {
    @Autowired
    public SurgeryDeptPrivCache(SurgeryDeptPrivMapper surgeryDeptPrivMapper) {
        super(new Converter<String, List<SurgeryDeptPriv>>() {
            @Override
            public List<SurgeryDeptPriv> convert(String icdCode) {
                var queryWrapper = new LambdaQueryWrapper<SurgeryDeptPriv>();
                queryWrapper.eq(SurgeryDeptPriv::getIcdCode, icdCode);
                queryWrapper.eq(SurgeryDeptPriv::getValid, true);
                return surgeryDeptPrivMapper.selectList(queryWrapper);
            }
        });
    }
}
