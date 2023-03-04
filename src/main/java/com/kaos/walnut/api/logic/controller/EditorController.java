package com.kaos.walnut.api.logic.controller;

import javax.validation.Valid;

import com.kaos.walnut.api.logic.service.EditorService;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/edit")
public class EditorController {
    /**
     * 业务
     */
    @Autowired
    EditorService editorService;

    /**
     * 伪造PACU数据
     * 
     * @param data
     * @return
     */
    @ApiName("伪造PACU数据")
    @RequestMapping(value = "makeFakePacuData", method = RequestMethod.POST, produces = MediaType.EXCEL)
    Workbook makeFakePacuData(@RequestBody @Valid Workbook data) {
        return this.editorService.makeFakeData(data);
    }
}
