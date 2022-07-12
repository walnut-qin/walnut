package com.kaos.skynet.core.api.data.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KaosUserMapperTests {
    @Autowired
    KaosUserMapper kaosUserMapper;

    @Test
    void selectById() {
        kaosUserMapper.selectById("0306");
    }
}
