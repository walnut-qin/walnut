package com.kaos.walnut.api.logic.controller.surgery;

import com.kaos.walnut.api.logic.service.surgery.DictionaryService;
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
@RequestMapping("/api/surgery/dictionary")
public class DictionaryController {
    @Autowired
    DictionaryService dictionaryService;

    /**
     * 批量导入权限
     * 
     * @param workbook
     * @return
     */
    @ApiName("导入手术授权数据")
    @RequestMapping(value = "importSurgery", method = RequestMethod.POST, produces = MediaType.JSON)
    Integer importSurgery(@RequestBody Workbook workbook) {
        return this.dictionaryService.importSurgery(workbook);
    }
}
