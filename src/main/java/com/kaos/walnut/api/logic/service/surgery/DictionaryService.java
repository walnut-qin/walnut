package com.kaos.walnut.api.logic.service.surgery;

import java.util.Map;
import java.util.Queue;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.kaos.walnut.api.data.entity.DawnOrgDept;
import com.kaos.walnut.api.data.entity.DawnOrgEmpl;
import com.kaos.walnut.api.data.entity.MetComIcdOperation;
import com.kaos.walnut.api.data.enums.ValidStateEnum;
import com.kaos.walnut.api.data.mapper.DawnOrgDeptMapper;
import com.kaos.walnut.api.data.mapper.DawnOrgEmplMapper;
import com.kaos.walnut.api.data.mapper.MetComIcdOperationMapper;
import com.kaos.walnut.core.util.StringUtils;

import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
    private Map<String, Pair<DawnOrgDept, Queue<DawnOrgEmpl>>> checkSheet(Sheet sheet) {
        // 构造响应
        Map<String, Pair<DawnOrgDept, Queue<DawnOrgEmpl>>> result = Maps.newHashMap();
        Boolean passed = true;

        // 校验表头
        if (!sheet.getRow(2).getCell(1).getStringCellValue().equals("授权科室")
                || !sheet.getRow(3).getCell(1).getStringCellValue().equals("手术编码")
                || !sheet.getRow(3).getCell(2).getStringCellValue().equals("手术名称")) {
            throw new RuntimeException("文件模板校验失败");
        }

        // 校验科室信息
        var deptName = sheet.getRow(2).getCell(2).getStringCellValue().trim();
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
            // 跳过表头
            switch (row.getRowNum()) {
                case 0:
                case 1:
                case 2:
                case 3:
                    continue;

                default:
                    break;
            }

            // 读取ICD编码和ICD名称
            var icd = row.getCell(1).getStringCellValue();
            if (StringUtils.isBlank(icd)) {
                continue;
            }
            var icdName = row.getCell(2).getStringCellValue();
            // 检索ICD手术实体
            var surgery = this.icdMapper.selectById(icd);
            // 校验ICD手术实体有效性
            if (surgery == null) {
                passed = false;
                log.error(String.format("手术<%s, %s>校验失败: 手术未维护", icd, icdName));
                continue;
            } else if (surgery.getValidState() != ValidStateEnum.在用) {
                passed = false;
                log.error(String.format("手术<%s, %s>校验失败: 手术已作废", icd, icdName));
                continue;
            }

            // 医师校验
            Queue<DawnOrgEmpl> docCodes = Queues.newArrayDeque();
            for (Integer i = 3; i < row.getLastCellNum(); i++) {
                // 读取医生姓名
                var docName = row.getCell(i).getStringCellValue().trim();
                if (StringUtils.isBlank(docName)) {
                    continue;
                }

                // 检索医生实体
                var wrapper = new QueryWrapper<DawnOrgEmpl>().lambda();
                wrapper.eq(DawnOrgEmpl::getValidState, ValidStateEnum.在用);
                wrapper.eq(DawnOrgEmpl::getEmplName, docName);
                wrapper.eq(DawnOrgEmpl::getDeptCode, depts.get(0).getDeptCode());
                var doctors = this.emplMapper.selectList(wrapper);

                // 检验医生实体有效性
                if (doctors.size() == 0) {
                    passed = false;
                    log.error(String.format("医师<%s>校验失败: 医师不属于科室<%s>或医师不存在或已停用", deptName, docName));
                    continue;
                } else if (doctors.size() > 1) {
                    passed = false;
                    log.error(String.format("医师<%s>校验失败: 存在同名医师", docName));
                    continue;
                }

                // 将医师实体加入结果集
                docCodes.add(doctors.get(0));
            }

            // 加入结果集
            result.put(surgery.getIcdCode(), new Pair<>(depts.get(0), docCodes));
        }

        // 最终判定
        if (!passed) {
            throw new RuntimeException("数据行校验失败, 详细异常请查看服务器日志");
        }

        return result;
    }

    /**
     * 校验医师信息的有效性
     * 
     * @param row excel行对象
     * @return
     */
    private Queue<DawnOrgEmpl> checkDoctors(DawnOrgDept dept, MetComIcdOperation icd, Row row) throws LogException {
        // 声明 结果集 和 异常集
        Queue<DawnOrgEmpl> doctors = Queues.newArrayDeque();
        Queue<String> errors = Queues.newArrayDeque();

        // 轮训所有的单元格
        for (Integer i = 3; i < row.getLastCellNum(); i++) {
            // 跳过空行
            var name = row.getCell(i).getStringCellValue().trim();
            if (StringUtils.isBlank(name)) {
                continue;
            }

            // 检索符合条件的医师
            var wrapper = new QueryWrapper<DawnOrgEmpl>().lambda();
            wrapper.eq(DawnOrgEmpl::getValidState, ValidStateEnum.在用);
            wrapper.eq(DawnOrgEmpl::getEmplName, name);
            wrapper.eq(DawnOrgEmpl::getDeptCode, dept.getDeptCode());
            var rets = this.emplMapper.selectList(wrapper);
            if (rets.isEmpty()) {
                errors.add(String.format("[%s]校验失败: 不存在满足条件的账号", name));
            } else if (rets.size() > 1) {
                errors.add(String.format("[%s]校验失败: 存在同名账号", name));
            } else {
                doctors.add(rets.get(0));
            }
        }

        // 判断响应
        if (errors.isEmpty()) {
            return doctors;
        } else {
            throw new LogException(errors);
        }
    }

    /**
     * 用于记录日志的异常
     */
    static class LogException extends RuntimeException {
        /**
         * 异常信息
         */
        @Getter
        public Object errInfo;

        /**
         * 构造函数
         * 
         * @param errInfo
         */
        public LogException(Object errInfo) {
            this.errInfo = errInfo;
        }
    }
}
