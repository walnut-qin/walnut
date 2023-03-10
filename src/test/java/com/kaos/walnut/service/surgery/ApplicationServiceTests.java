package com.kaos.walnut.service.surgery;

import com.kaos.walnut.api.logic.service.surgery.ApplicationService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationServiceTests {
    @Autowired
    ApplicationService applicationService;

    @Test
    void getInfo() {
        this.applicationService.getInfo("applyNo");
    }
}
