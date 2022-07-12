package com.kaos.skynet.core.api.data.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaos.skynet.core.api.data.entity.KaosUserAccess;

@DS("his")
public interface KaosUserAccessMapper extends BaseMapper<KaosUserAccess> {

}
