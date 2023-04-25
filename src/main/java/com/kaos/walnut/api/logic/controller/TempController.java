package com.kaos.walnut.api.logic.controller;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.kaos.walnut.api.data.entity.MetComIcdOperationGrantType.GrantTypeEnum;
import com.kaos.walnut.api.logic.service.surgery.DictionaryService;
import com.kaos.walnut.api.logic.service.surgery.privilege.EmplGrantService;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.StringUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/temp")
public class TempController {
    @Autowired
    EmplGrantService emplGrantService;

    @Autowired
    DictionaryService dictionaryService;

    @ApiName("trans")
    @RequestMapping(value = "trans", method = RequestMethod.POST, produces = MediaType.JSON)
    Object trans(@RequestBody @Valid Workbook workbook) throws IOException {
        Map<String, Integer> lvlMap = Maps.newHashMap();
        Multimap<String, String> emplMultiMap = HashMultimap.create();

        // 读取原始数据
        var sheet = workbook.getSheetAt(0);
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            var row = sheet.getRow(i);

            // 读取编码
            var icdCode = row.getCell(0).toString().trim();
            if (StringUtils.isBlank(icdCode)) {
                icdCode = row.getCell(1).toString().trim();
            }

            // 读取手术等级
            lvlMap.put(icdCode, Double.valueOf(row.getCell(5).toString()).intValue());

            // 读取医师列表
            for (int j = 6; j < row.getLastCellNum(); j++) {
                var cell = row.getCell(j);
                var emplCode = StringUtils.EMPTY;
                switch (cell.getCellType()) {
                    case NUMERIC:
                        emplCode += Double.valueOf(cell.getNumericCellValue()).intValue();
                        break;

                    case STRING:
                        emplCode += cell.getStringCellValue().trim();
                        break;

                    default:
                        emplCode += cell.toString().trim();
                        break;
                }
                if (StringUtils.isBlank(emplCode)) {
                    continue;
                }
                emplMultiMap.put(icdCode, StringUtils.leftPad(emplCode, 6, '0'));
            }
        }

        // 修改手术等级
        for (var icdCode : lvlMap.keySet()) {
            this.dictionaryService.changeLevel(icdCode, lvlMap.get(icdCode));
        }

        // 授权
        Integer cnt = 0;
        for (var icdCode : emplMultiMap.asMap().keySet()) {
            log.info(String.format("开始授权 - %d-%s", ++cnt, icdCode));
            this.emplGrantService.grant(icdCode, emplMultiMap.get(icdCode), GrantTypeEnum.普通手术);
        }

        return lvlMap;
    }
}
