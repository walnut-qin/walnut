package com.kaos.walnut.api.logic.controller;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.kaos.walnut.api.data.cache.DawnOrgDeptCache;
import com.kaos.walnut.api.data.entity.DawnOrgEmpl;
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

    /**
     * 科室编码
     */
    private String deptId;

    /**
     * 职工字典
     */
    private Map<String, String> emplDict = Maps.newHashMap();

    @ApiName("切换科室")
    @RequestMapping(value = "updateDept", method = RequestMethod.POST, produces = MediaType.JSON)
    String updateDept(@RequestBody @Valid UpdateDept.ReqBody reqBody) {
        var dept = this.dawnOrgDeptCache.get(reqBody.deptId);
        if (dept == null) {
            throw new RuntimeException("科室不存在-" + reqBody.deptId);
        }
        this.deptId = reqBody.deptId;

        return dept.getDeptName();
    }

    static class UpdateDept {
        static class ReqBody {
            String deptId;
        }
    }

    private String getEmplIdFromCell(Cell cell) {
        // 判空
        if (cell == null) {
            return null;
        }

        String code = null;
        switch (cell.getCellType()) {
            case NUMERIC:
                code = "" + Double.valueOf(cell.getNumericCellValue()).intValue();
                break;

            case STRING:
                code = cell.getStringCellValue();
                break;

            default:
                code = cell.toString();
                break;
        }
        if (!code.startsWith("00")) {
            code = "00" + code;
        }
        return code.trim();
    }

    @ApiName("更新职工字典")
    @RequestMapping(value = "updateEmplDict", method = RequestMethod.POST, produces = MediaType.JSON)
    Map<String, String> updateEmplDict(@RequestBody @Valid Workbook workbook) {
        // 清空当前字典
        this.emplDict.clear();

        // 锚定表单
        var sheet = workbook.getSheetAt(0);
        for (Integer i = 4; i <= sheet.getLastRowNum(); i++) {
            // 读取行
            var row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            // 参数校验
            var name = row.getCell(0) == null ? null : row.getCell(0).toString().trim();
            var code = this.getEmplIdFromCell(row.getCell(2));
            if (StringUtils.isBlank(name) || StringUtils.isBlank(code)) {
                continue;
            }

            this.emplDict.put(name, code);
        }

        return this.emplDict;
    }

    @ApiName("医师授权")
    @RequestMapping(value = "grant", method = RequestMethod.POST, produces = MediaType.JSON)
    Map<String, Collection<String>> grant(@RequestBody @Valid Workbook workbook) {
        // 创建辅助结构
        Multimap<String, String> data = ArrayListMultimap.create();

        // 锚定表单
        var sheet = workbook.getSheetAt(0);
        for (Integer i = 1; i <= sheet.getLastRowNum(); i++) {
            // 读取行
            var row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            // 读取ICD编码
            String icdCode = null;
            if (row.getCell(0) != null && !StringUtils.isBlank(row.getCell(0).toString())) {
                icdCode = row.getCell(0).toString().trim();
            } else if (row.getCell(1) != null && !StringUtils.isBlank(row.getCell(1).toString())) {
                icdCode = row.getCell(1).toString().trim();
            }
            if (StringUtils.isBlank(icdCode)) {
                continue;
            }

            // 读取医生
            for (Integer j = 6; j <= row.getLastCellNum(); j++) {
                // 读取单元格
                var cell = row.getCell(j);

                // 判空
                if (cell == null || StringUtils.isBlank(cell.toString())) {
                    continue;
                }

                // 记录结果
                data.put(icdCode, cell.toString().trim());
            }
        }

        // 授权过程
        for (var icdCode : data.keys()) {
            var docCodes = data.get(icdCode);

            // 科室授权
            this.deptGrantService.grant(icdCode, this.deptId);

            // 医师授权
            this.emplGrantService.grant(icdCode, docCodes.stream().map(x -> {
                // 先取字典
                if (this.emplDict.containsKey(x)) {
                    return this.emplDict.get(x);
                }

                // 再查数据库
                var wrapper = new QueryWrapper<DawnOrgEmpl>().lambda();
                wrapper.eq(DawnOrgEmpl::getEmplName, x);
                wrapper.eq(DawnOrgEmpl::getValidState, 1);
                var empls = this.dawnOrgEmplMapper.selectList(wrapper);
                if (empls.size() == 1) {
                    this.emplDict.put(x, empls.get(0).getEmplCode());
                    return this.emplDict.get(x);
                }

                log.error("fatal error");
                throw new RuntimeException("医师字典不存在-" + x);
            }).toList());
        }

        log.info("授权完毕");

        return data.asMap();
    }
}
