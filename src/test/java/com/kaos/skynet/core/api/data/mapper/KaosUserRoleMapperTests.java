package com.kaos.skynet.core.api.data.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaos.skynet.core.api.data.entity.KaosUserRole;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KaosUserRoleMapperTests {
    @Autowired
    KaosUserRoleMapper kaosUserRoleMapper;

    @Test
    void selectBatchIds() {
        QueryWrapper<KaosUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("USER_CODE", "0306");
        kaosUserRoleMapper.selectList(wrapper);
    }

    @Test
    void selectByMultiId() {
        kaosUserRoleMapper.selectByMultiId("0306", "charge");
    }
}
