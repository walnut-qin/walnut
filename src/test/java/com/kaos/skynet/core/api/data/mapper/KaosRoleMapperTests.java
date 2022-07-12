package com.kaos.skynet.core.api.data.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KaosRoleMapperTests {
    @Autowired
    KaosRoleMapper kaosRoleMapper;

    @Test
    void selectById() {
        kaosRoleMapper.selectById("admin");
    }
}
