package com.walnut.core.api.data.cache;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.walnut.core.api.data.entity.KaosUserRole;
import com.walnut.core.api.data.mapper.KaosUserRoleMapper;
import com.walnut.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
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
