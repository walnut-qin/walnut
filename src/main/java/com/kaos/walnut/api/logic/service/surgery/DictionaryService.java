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
import com.kaos.walnut.core.type.exceptions.LogException;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // 校验excel模板
        if (!sheet.getRow(2).getCell(1).getStringCellValue().equals("授权科室")
                || !sheet.getRow(3).getCell(1).getStringCellValue().equals("手术编码")
                || !sheet.getRow(3).getCell(2).getStringCellValue().equals("手术名称")) {
            throw new RuntimeException("文件模板校验失败");
        }

        // 校验表头 - 科室信息
        var deptName = sheet.getRow(2).getCell(2).getStringCellValue().trim();
        if (StringUtils.isBlank(deptName)) {
            throw new RuntimeException("excel表中未指定科室");
        }

        // 检索科室实体
        var queryWrapper = new QueryWrapper<DawnOrgDept>().lambda();
        queryWrapper.eq(DawnOrgDept::getDeptName, deptName);
        queryWrapper.eq(DawnOrgDept::getValidState, ValidStateEnum.在用);
        var depts = this.deptMapper.selectList(queryWrapper);
        if (depts.isEmpty()) {
            throw new RuntimeException(String.format("科室<%s>校验失败: 不存在或已停用", deptName));
        } else if (depts.size() > 1) {
            throw new RuntimeException(String.format("科室<%s>校验失败: 存在同名科室", deptName));
        }

        // 校验body
        try {
            return this.checkBody(depts.get(0), sheet);
        } catch (LogException e) {
            log.error(ObjectUtils.serialize(e.getLogInfo()));
            throw new RuntimeException(String.format("科室<%s>授权信息校验失败: 明细请查看服务器日志", deptName));
        }
    }

    /**
     * 校验sheet的body
     * 
     * @param dept
     * @param sheet
     * @return
     * @throws LogException
     */
    private Map<String, Pair<DawnOrgDept, Queue<DawnOrgEmpl>>> checkBody(DawnOrgDept dept, Sheet sheet)
            throws LogException {
        // 声明异常集
        Map<String, Pair<DawnOrgDept, Queue<DawnOrgEmpl>>> result = Maps.newHashMap();
        Queue<Object> errors = Queues.newArrayDeque();

        // 轮训所有数据行
        for (int i = 4; i < sheet.getLastRowNum(); i++) {
            // 锚定行对象
            var row = sheet.getRow(i);

            // 跳过空行
            var icdCode = row.getCell(1).getStringCellValue().trim();
            if (StringUtils.isBlank(icdCode)) {
                continue;
            }

            // 检索符合条件的手术记录
            var icd = this.icdMapper.selectById(icdCode);
            if (icd == null || icd.getValidState() != ValidStateEnum.在用) {
                errors.add(String.format("手术<%s>校验失败: 手术未维护", icdCode));
                continue;
            }

            // 校验医师
            try {
                var doctors = this.checkDoctors(dept, icd, row);
                result.put(icd.getIcdCode(), new Pair<DawnOrgDept, Queue<DawnOrgEmpl>>(dept, doctors));
            } catch (LogException e) {
                var node = Maps.newHashMap();
                node.put(icd.getIcdCode(), e.getLogInfo());
                errors.add(node);
            }
        }

        // 判断响应
        if (errors.isEmpty()) {
            return result;
        } else {
            throw new LogException(errors);
        }
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
        Queue<Object> errors = Queues.newArrayDeque();

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
}
