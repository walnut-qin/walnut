package com.kaos.skynet.core.api.data.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KaosUserAccessMapperTests {
    @Autowired
    KaosUserAccessMapper kaosUserAccessMapper;

    @Test
    void selectById() {
        kaosUserAccessMapper.selectById("0306");
    }
}
