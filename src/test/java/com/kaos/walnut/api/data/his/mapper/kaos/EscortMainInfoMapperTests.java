package com.kaos.walnut.api.data.his.mapper.kaos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortMainInfoMapperTests {
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    @Test
    void selectByPatient() {
        escortMainInfoMapper.selectByPatient("2009999999", true);
    }

    @Test
    void selectByHelper() {
        escortMainInfoMapper.selectByHelper("0123456789", true);
    }
}
