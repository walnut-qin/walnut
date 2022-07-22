package com.walnut.core.api.data.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.walnut.core.api.data.entity.KaosUserRole;

@DS("walnut")
public interface KaosUserRoleMapper extends BaseMapper<KaosUserRole> {
}
