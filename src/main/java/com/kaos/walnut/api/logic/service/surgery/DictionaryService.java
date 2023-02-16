package com.kaos.walnut.api.logic.service.surgery;

import java.util.Map;
import java.util.Queue;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.kaos.walnut.api.data.entity.DawnOrgDept;
import com.kaos.walnut.api.data.entity.DawnOrgEmpl;
import com.kaos.walnut.api.data.enums.ValidStateEnum;
import com.kaos.walnut.api.data.mapper.DawnOrgDeptMapper;
import com.kaos.walnut.api.data.mapper.DawnOrgEmplMapper;
import com.kaos.walnut.api.data.mapper.MetComIcdOperationMapper;

import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DictionaryService {
    /**
     * 手术信息接口
     */
    @Autowired
    MetComIcdOperationMapper icdMapper;

    /**
     * 科室信息接口
     */
    @Autowired
    DawnOrgDeptMapper deptMapper;

    /**
     * 人员信息接口
     */
    @Autowired
    DawnOrgEmplMapper emplMapper;

    @Transactional
    public Integer importSurgery(@NotNull Workbook workbook) {
        // 读取sheet
        var sheet = workbook.getSheetAt(0);

        // 校验数据
        var data = this.checkSheet(sheet);

        return data.size();
    }

    /**
     * 逻辑校验
     * 
     * @param sheet
     * @return
     */
    private Map<String, Pair<String, Queue<String>>> checkSheet(Sheet sheet) {
        // 构造响应
        Map<String, Pair<String, Queue<String>>> result = Maps.newHashMap();

        // 校验header
        var header = sheet.getRow(0);
        if (!header.getCell(0).getStringCellValue().equals("手术编码")) {
            throw new RuntimeException("列信息校验失败: 首列不是手术编码");
        } else if (!header.getCell(4).getStringCellValue().equals("授权医师")) {
            throw new RuntimeException("列信息校验失败: 第5列不是授权医师");
        }

        // 校验科室信息
        var deptName = sheet.getSheetName().trim();
        var queryWrapper = new QueryWrapper<DawnOrgDept>().lambda();
        queryWrapper.eq(DawnOrgDept::getDeptName, deptName);
        queryWrapper.eq(DawnOrgDept::getValidState, ValidStateEnum.在用);
        var depts = this.deptMapper.selectList(queryWrapper);
        if (depts.size() == 0) {
            throw new RuntimeException(String.format("科室<%s>校验失败: 科室不存在或已停用", deptName));
        } else if (depts.size() > 1) {
            throw new RuntimeException(String.format("科室<%s>校验失败: 存在同名科室", deptName));
        }

        // 行校验
        for (var row : sheet) {
            // 跳过header
            if (row.getRowNum() == 0) {
                continue;
            }

            // 手术校验
            var icd = row.getCell(0).getStringCellValue();
            var icdName = row.getCell(1).getStringCellValue();
            var surgery = this.icdMapper.selectById(icd);
            if (surgery == null) {
                throw new RuntimeException(String.format("手术<%s, %s>校验失败: 手术未维护", icd, icdName));
            } else if (surgery.getValidState() != ValidStateEnum.在用) {
                throw new RuntimeException(String.format("手术<%s, %s>校验失败: 手术已作废", icd, icdName));
            }

            // 医师校验
            Queue<String> docCodes = Queues.newArrayDeque();
            for (Integer i = 4; i < row.getLastCellNum(); i++) {
                // 校验
                var docName = row.getCell(i).getStringCellValue().trim();
                var wrapper = new QueryWrapper<DawnOrgEmpl>().lambda();
                wrapper.eq(DawnOrgEmpl::getValidState, ValidStateEnum.在用);
                wrapper.eq(DawnOrgEmpl::getEmplName, docName);
                var doctors = this.emplMapper.selectList(wrapper);
                if (doctors.size() == 0) {
                    throw new RuntimeException(String.format("医师<%s>校验失败: 医师不存在或已停用", docName));
                } else if (doctors.size() > 1) {
                    throw new RuntimeException(String.format("医师<%s>校验失败: 存在同名医师", docName));
                }
                // 加入结果集
                docCodes.add(doctors.get(0).getEmplCode());
            }

            // 加入结果集
            result.put(surgery.getIcdCode(), new Pair<String, Queue<String>>(depts.get(0).getDeptCode(), docCodes));
        }

        return result;
    }
}
