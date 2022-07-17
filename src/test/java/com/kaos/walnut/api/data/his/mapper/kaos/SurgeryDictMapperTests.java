package com.kaos.walnut.api.data.his.mapper.kaos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SurgeryDictMapperTests {
    @Autowired
    SurgeryDictMapper surgeryDictMapper;

    @Test
    void selectGrantedList() {
        surgeryDictMapper.selectGrantedList("emplCode", "deptCode");
    }
}
