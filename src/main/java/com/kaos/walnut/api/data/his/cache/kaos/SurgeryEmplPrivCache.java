package com.kaos.walnut.api.data.his.cache.kaos;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kaos.walnut.api.data.his.entity.kaos.SurgeryEmplPriv;
import com.kaos.walnut.api.data.his.mapper.kaos.SurgeryEmplPrivMapper;
import com.kaos.walnut.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SurgeryEmplPrivCache extends Cache<String, List<SurgeryEmplPriv>> {
    @Autowired
    public SurgeryEmplPrivCache(SurgeryEmplPrivMapper surgeryEmplPrivMapper) {
        super(new Converter<String, List<SurgeryEmplPriv>>() {
            @Override
            public List<SurgeryEmplPriv> convert(String icdCode) {
                var queryWrapper = new LambdaQueryWrapper<SurgeryEmplPriv>();
                queryWrapper.eq(SurgeryEmplPriv::getIcdCode, icdCode);
                queryWrapper.eq(SurgeryEmplPriv::getValid, true);
                return surgeryEmplPrivMapper.selectList(queryWrapper);
            }
        });
    }
}
