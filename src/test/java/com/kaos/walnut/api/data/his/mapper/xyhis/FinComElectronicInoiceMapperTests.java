package com.kaos.walnut.api.data.his.mapper.xyhis;

import com.kaos.walnut.api.data.his.enums.TransTypeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinComElectronicInoiceMapperTests {
    @Autowired
    FinComElectronicInvoiceMapper finComElectronicInvoiceMapper;

    @Test
    void selectByMultiId(){
        finComElectronicInvoiceMapper.selectByMultiId("200917009430", TransTypeEnum.Positive);
    }
}
