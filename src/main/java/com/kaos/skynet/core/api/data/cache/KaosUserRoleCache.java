package com.kaos.skynet.core.api.data.cache;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaos.skynet.core.api.data.entity.KaosUserRole;
import com.kaos.skynet.core.api.data.mapper.KaosUserRoleMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class KaosUserRoleCache extends Cache<String, List<KaosUserRole>> {
    @Autowired
    public KaosUserRoleCache(KaosUserRoleMapper kaosUserRoleMapper) {
        super(new Converter<String, List<KaosUserRole>>() {
            @Override
            public List<KaosUserRole> convert(String userCode) {
                QueryWrapper<KaosUserRole> wrapper = new QueryWrapper<>();
                wrapper.eq("USER_CODE", userCode);
                return kaosUserRoleMapper.selectList(wrapper);
            }
        });
    }
}
