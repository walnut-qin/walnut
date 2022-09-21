package com.kaos.walnut.api.data.docare.mapper.medsurgery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MedAnesthesiaPlanMapperTests {
    @Autowired
    MedAnesthesiaPlanMapper medAnesthesiaPlanMapper;

    @Test
    void selectByMultiId() {
        // medAnesthesiaPlanMapper.selectByMultiId("0123456789", 1, 1);
    }
}
