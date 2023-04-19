package com.kaos.walnut.api.logic.controller;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.cache.DawnOrgDeptCache;
import com.kaos.walnut.api.data.entity.DawnOrgEmpl;
import com.kaos.walnut.api.data.enums.ValidStateEnum;
import com.kaos.walnut.api.data.mapper.DawnOrgEmplMapper;
import com.kaos.walnut.api.logic.service.surgery.privilege.DeptGrantService;
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
    DawnOrgDeptCache dawnOrgDeptCache;

    @Autowired
    DeptGrantService deptGrantService;

    @Autowired
    EmplGrantService emplGrantService;

    @Autowired
    DawnOrgEmplMapper dawnOrgEmplMapper;

    @ApiName("tmp")
    @RequestMapping(value = "trans", method = RequestMethod.POST, produces = MediaType.EXCEL)
    Object trans(@RequestBody @Valid Workbook workbook) throws IOException {
        // 构造员工字典
        Map<String, String> emplDict = Maps.newHashMap();

        var result = new XSSFWorkbook();
        Integer i = 0;
        Integer j = 0;
        try {
            // 构造结果表单
            var newSheet = result.createSheet("HIS号分析结果");
            var header = newSheet.createRow(0);
            header.createCell(0, CellType.STRING).setCellValue("主要编码");
            header.createCell(1, CellType.STRING).setCellValue("附加编码");
            header.createCell(2, CellType.STRING).setCellValue("手术名称");
            header.createCell(3, CellType.STRING).setCellValue("类别");
            header.createCell(4, CellType.STRING).setCellValue("录入选项");
            header.createCell(5, CellType.STRING).setCellValue("手术级别");

            // 设置特殊背景色
            var style = result.createCellStyle();
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(IndexedColors.YELLOW.index);

            // 分析原始表单
            var sheet = workbook.getSheetAt(0);
            for (i = 1; i < sheet.getLastRowNum(); i++) {
                var row = sheet.getRow(i);
                var newRow = newSheet.createRow(i);
                for (j = 0; j < row.getLastCellNum(); j++) {
                    var cell = row.getCell(j);
                    var newCell = newRow.createCell(j, CellType.STRING);

                    // 逻辑部分
                    if (j < 5) {
                        newCell.setCellValue(cell.toString());
                    } else if (j == 5) {
                        newCell.setCellValue(Double.valueOf(cell.toString()).intValue());
                    } else {
                        var emplName = cell.toString().trim().replaceAll(" ", "");

                        // 更新字典
                        if (!emplDict.containsKey(emplName)) {
                            var wrapper = new QueryWrapper<DawnOrgEmpl>().lambda();
                            wrapper.eq(DawnOrgEmpl::getValidState, ValidStateEnum.在用);
                            wrapper.eq(DawnOrgEmpl::getEmplName, emplName);
                            var empls = this.dawnOrgEmplMapper.selectList(wrapper);
                            if (empls.size() == 1) {
                                emplDict.put(emplName, empls.get(0).getEmplCode());
                            } else {
                                emplDict.put(emplName, null);
                            }
                        }

                        // 设置单元格
                        var emplCode = emplDict.get(emplName);
                        if (StringUtils.isBlank(emplCode)) {
                            newCell.setCellValue(emplName);
                            if (!StringUtils.isBlank(emplName)) {
                                newCell.setCellStyle(style);
                            }
                        } else {
                            newCell.setCellValue(emplCode);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error(String.format("location: %d, %d", i, j));
            throw new RuntimeException(ex.getMessage());
        }

        return result;
    }
}
