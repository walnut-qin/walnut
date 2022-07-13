package com.kaos.skynet.core.frame.api.data.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KaosUserRoleMapperTests {
    @Autowired
    KaosUserRoleMapper kaosUserRoleMapper;

    @Test
    void selectByMultiId() {
        kaosUserRoleMapper.selectByMultiId("0306", "charge");
    }
}
