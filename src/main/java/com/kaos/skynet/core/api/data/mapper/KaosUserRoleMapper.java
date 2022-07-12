package com.kaos.skynet.core.api.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaos.skynet.core.api.data.entity.KaosUserRole;

public interface KaosUserRoleMapper extends BaseMapper<KaosUserRole> {
    /**
     * 主键检索
     * 
     * @return
     */
    KaosUserRole selectByMultiId(String userCode, String role);
}
