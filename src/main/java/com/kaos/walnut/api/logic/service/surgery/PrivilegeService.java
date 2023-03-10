package com.kaos.walnut.api.logic.service.surgery;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
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
public class PrivilegeService {
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

        // 执行授权
        return this.updatePrivilege(data);
    }

    /**
     * 逻辑校验
     * 
     * @param sheet
     * @return
     */
    private Map<MetComIcdOperation, Pair<DawnOrgDept, Queue<DawnOrgEmpl>>> checkSheet(Sheet sheet) {
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
    private Map<MetComIcdOperation, Pair<DawnOrgDept, Queue<DawnOrgEmpl>>> checkBody(DawnOrgDept dept, Sheet sheet)
            throws LogException {
        // 声明异常集
        Map<MetComIcdOperation, Pair<DawnOrgDept, Queue<DawnOrgEmpl>>> result = Maps.newHashMap();
        Queue<Object> errors = Queues.newArrayDeque();

        // 轮训所有数据行
        for (int i = 4; i < sheet.getLastRowNum(); i++) {
            // 记录debug日志
            log.debug(String.format("读取excel表单第 %d 行", i));

            // 锚定行对象
            var row = sheet.getRow(i);

            // 跳过空行
            var icdCode = row.getCell(1).toString().trim();
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
                result.put(icd, new Pair<DawnOrgDept, Queue<DawnOrgEmpl>>(dept, doctors));
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
            // 记录debug日志
            log.debug(String.format("读取excel表单第 %d 列", i));

            // 跳过空行
            var name = row.getCell(i).toString().trim();
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
     * 执行授权
     * 
     * @param data
     */
    private Integer updatePrivilege(Map<MetComIcdOperation, Pair<DawnOrgDept, Queue<DawnOrgEmpl>>> data) {
        // 轮训修改
        for (var icd : data.keySet()) {
            // 锚定新数据
            var priv = data.get(icd);

            // 更新科室信息
            this.updateDept(icd, priv.getFirst());

            // 更新医生信息
            this.updateDoctors(icd, priv.getSecond());

            // 更新数据库
            var wrapper = new UpdateWrapper<MetComIcdOperation>().lambda();
            wrapper.eq(MetComIcdOperation::getIcdCode, icd.getIcdCode());
            wrapper.set(MetComIcdOperation::getDeptCode, icd.getDeptCode());
            wrapper.set(MetComIcdOperation::getDeptName, icd.getDeptName());
            wrapper.set(MetComIcdOperation::getDocCode, icd.getDocCode());
            wrapper.set(MetComIcdOperation::getDocName, icd.getDocName());
            this.icdMapper.update(null, wrapper);
        }

        return data.size();
    }

    /**
     * 修改科室信息
     * 
     * @param icd
     * @param dept
     */
    private void updateDept(MetComIcdOperation icd, DawnOrgDept dept) {
        // 读取记录中的编码字段
        var codes = Lists.newArrayList();
        if (!StringUtils.isBlank(icd.getDeptCode())) {
            codes.addAll(Arrays.asList(icd.getDeptCode().split("\\|")));
        }
        if (!codes.contains(dept.getDeptCode())) {
            codes.add(dept.getDeptCode());
            icd.setDeptCode(StringUtils.join(codes, "|"));
        }

        // 读取记录中的名称字段
        var names = Lists.newArrayList();
        if (!StringUtils.isBlank(icd.getDeptName())) {
            names.addAll(Arrays.asList(icd.getDeptName().split("\\|")));
        }
        if (!names.contains(dept.getDeptName())) {
            names.add(dept.getDeptName());
            icd.setDeptName(StringUtils.join(names, "|"));
        }
    }

    /**
     * 修改医生信息
     * 
     * @param icd
     * @param dept
     */
    private void updateDoctors(MetComIcdOperation icd, Collection<DawnOrgEmpl> doctors) {
        // 构造初始容器
        var codes = Lists.newArrayList();
        if (!StringUtils.isBlank(icd.getDocCode())) {
            codes.addAll(Arrays.asList(icd.getDocCode().split("\\|")));
        }
        var names = Lists.newArrayList();
        if (!StringUtils.isBlank(icd.getDocName())) {
            names.addAll(Arrays.asList(icd.getDocName().split("\\|")));
        }

        // 轮训修改
        for (DawnOrgEmpl doctor : doctors) {
            if (!codes.contains(doctor.getEmplCode())) {
                codes.add(doctor.getEmplCode());
                icd.setDocCode(StringUtils.join(codes, "|"));
            }
            if (!names.contains(doctor.getEmplName())) {
                names.add(doctor.getEmplName());
                icd.setDocName(StringUtils.join(names, "|"));
            }
        }
    }

    @Transactional
    public Integer importDocPriv(Workbook data) {
        // 锚定sheet
        var sheet = data.getSheetAt(0);

        // 构造医师列表
        var doct = this.emplMapper.selectById("007928");
        var dept = this.deptMapper.selectById(doct.getDeptCode());
        List<DawnOrgEmpl> doctors = Lists.newArrayList();
        doctors.add(doct);

        // 轮训
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            // 锚定行
            var row = sheet.getRow(i);

            // 丢编码
            var icdCode = row.getCell(0).toString();

            // 检索手术字典记录
            var icd = this.icdMapper.selectById(icdCode);
            if (icd == null) {
                throw new RuntimeException("手术不存在" + icdCode);
            }

            // 添加科室
            this.updateDept(icd, dept);

            // 添加权限
            this.updateDoctors(icd, doctors);

            // 更新数据库
            var wrapper = new UpdateWrapper<MetComIcdOperation>().lambda();
            wrapper.eq(MetComIcdOperation::getIcdCode, icd.getIcdCode());
            wrapper.set(MetComIcdOperation::getDeptCode, icd.getDeptCode());
            wrapper.set(MetComIcdOperation::getDeptName, icd.getDeptName());
            wrapper.set(MetComIcdOperation::getDocCode, icd.getDocCode());
            wrapper.set(MetComIcdOperation::getDocName, icd.getDocName());
            this.icdMapper.update(null, wrapper);
        }

        return sheet.getLastRowNum();
    }

    /**
     * 删除某个医师的某个权限
     * 
     * @param icd
     * @param doctor
     */
    private void clearPrivilege(MetComIcdOperation icd, DawnOrgDept department) {
        // 解析权限字段
        List<String> codes = Lists.newArrayList();
        if (!StringUtils.isBlank(icd.getDeptCode())) {
            codes.addAll(Arrays.asList(icd.getDeptCode().split("\\|")));
        }
        List<String> names = Lists.newArrayList();
        if (!StringUtils.isBlank(icd.getDeptName())) {
            names.addAll(Arrays.asList(icd.getDeptName().split("\\|")));
        }

        // 删除医师信息
        if (codes.contains(department.getDeptCode())) {
            codes.remove(department.getDeptCode());
            icd.setDeptCode(StringUtils.join(codes, "|"));
        }
        if (names.contains(department.getDeptName())) {
            names.remove(department.getDeptName());
            icd.setDeptName(StringUtils.join(names, "|"));
        }

        // 回写数据库
        var wrapper = new UpdateWrapper<MetComIcdOperation>().lambda();
        wrapper.eq(MetComIcdOperation::getIcdCode, icd.getIcdCode());
        wrapper.set(MetComIcdOperation::getDeptCode, icd.getDeptCode());
        wrapper.set(MetComIcdOperation::getDeptName, icd.getDeptName());
        this.icdMapper.update(null, wrapper);
    }

    /**
     * 删除某个医师的某个权限
     * 
     * @param icd
     * @param doctor
     */
    private void clearPrivilege(MetComIcdOperation icd, DawnOrgEmpl doctor) {
        // 解析权限字段
        List<String> codes = Lists.newArrayList();
        if (!StringUtils.isBlank(icd.getDocCode())) {
            codes.addAll(Arrays.asList(icd.getDocCode().split("\\|")));
        }
        List<String> names = Lists.newArrayList();
        if (!StringUtils.isBlank(icd.getDocName())) {
            names.addAll(Arrays.asList(icd.getDocName().split("\\|")));
        }

        // 删除医师信息
        if (codes.contains(doctor.getEmplCode())) {
            codes.remove(doctor.getEmplCode());
            icd.setDocCode(StringUtils.join(codes, "|"));
        }
        if (names.contains(doctor.getEmplName())) {
            names.remove(doctor.getEmplName());
            icd.setDocName(StringUtils.join(names, "|"));
        }

        // 回写数据库
        var wrapper = new UpdateWrapper<MetComIcdOperation>().lambda();
        wrapper.eq(MetComIcdOperation::getIcdCode, icd.getIcdCode());
        wrapper.set(MetComIcdOperation::getDocCode, icd.getDocCode());
        wrapper.set(MetComIcdOperation::getDocName, icd.getDocName());
        this.icdMapper.update(null, wrapper);
    }

    /**
     * 删除某个医师的某个权限
     * 
     * @param icd
     * @param doctor
     */
    private void addPrivilege(MetComIcdOperation icd, DawnOrgDept department) {
        // 解析权限字段
        List<String> codes = Lists.newArrayList();
        if (!StringUtils.isBlank(icd.getDeptCode())) {
            codes.addAll(Arrays.asList(icd.getDeptCode().split("\\|")));
        }
        List<String> names = Lists.newArrayList();
        if (!StringUtils.isBlank(icd.getDeptName())) {
            names.addAll(Arrays.asList(icd.getDeptName().split("\\|")));
        }

        // 删除医师信息
        if (!codes.contains(department.getDeptCode())) {
            codes.add(department.getDeptCode());
            icd.setDeptCode(StringUtils.join(codes, "|"));
        }
        if (!names.contains(department.getDeptName())) {
            names.add(department.getDeptName());
            icd.setDeptName(StringUtils.join(names, "|"));
        }

        // 回写数据库
        var wrapper = new UpdateWrapper<MetComIcdOperation>().lambda();
        wrapper.eq(MetComIcdOperation::getIcdCode, icd.getIcdCode());
        wrapper.set(MetComIcdOperation::getDeptCode, icd.getDeptCode());
        wrapper.set(MetComIcdOperation::getDeptName, icd.getDeptName());
        this.icdMapper.update(null, wrapper);
    }

    /**
     * 删除某个医师的某个权限
     * 
     * @param icd
     * @param doctor
     */
    private void addPrivilege(MetComIcdOperation icd, DawnOrgEmpl doctor) {
        // 解析权限字段
        List<String> codes = Lists.newArrayList();
        if (!StringUtils.isBlank(icd.getDocCode())) {
            codes.addAll(Arrays.asList(icd.getDocCode().split("\\|")));
        }
        List<String> names = Lists.newArrayList();
        if (!StringUtils.isBlank(icd.getDocName())) {
            names.addAll(Arrays.asList(icd.getDocName().split("\\|")));
        }

        // 添加医师信息
        if (!codes.contains(doctor.getEmplCode())) {
            codes.add(doctor.getEmplCode());
            icd.setDocCode(StringUtils.join(codes, "|"));
        }
        if (!names.contains(doctor.getEmplName())) {
            names.add(doctor.getEmplName());
            icd.setDocName(StringUtils.join(names, "|"));
        }

        // 回写数据库
        var wrapper = new UpdateWrapper<MetComIcdOperation>().lambda();
        wrapper.eq(MetComIcdOperation::getIcdCode, icd.getIcdCode());
        wrapper.set(MetComIcdOperation::getDocCode, icd.getDocCode());
        wrapper.set(MetComIcdOperation::getDocName, icd.getDocName());
        this.icdMapper.update(null, wrapper);
    }

    @Transactional
    public void clearDoctPrivilege(String docCode) {
        // 检索医生实体
        var doctor = this.emplMapper.selectById(docCode);
        if (doctor == null) {
            throw new RuntimeException("医师不存在");
        }

        // 检索所有有权限的手术
        var wrapper = new QueryWrapper<MetComIcdOperation>().lambda();
        wrapper.like(MetComIcdOperation::getDocCode, doctor.getEmplCode());
        var icds = this.icdMapper.selectList(wrapper);

        // 轮训删除医师权限
        for (var icd : icds) {
            this.clearPrivilege(icd, doctor);
        }
    }

    @Transactional
    public void clearDeptPrivilege(String deptCode) {
        // 检索医生实体
        var department = this.deptMapper.selectById(deptCode);
        if (department == null) {
            throw new RuntimeException("科室不存在");
        }

        // 检索所有有权限的手术
        var wrapper = new QueryWrapper<MetComIcdOperation>().lambda();
        wrapper.like(MetComIcdOperation::getDeptCode, department.getDeptCode());
        var icds = this.icdMapper.selectList(wrapper);

        // 轮训删除医师权限
        for (var icd : icds) {
            this.clearPrivilege(icd, department);
        }
    }

    @Transactional
    public void addDoctPrivilege(String docCode, List<String> icdCodes) {
        // 检索医生实体
        var doctor = this.emplMapper.selectById(docCode);
        if (doctor == null) {
            throw new RuntimeException("医师不存在");
        }

        // 轮训删除医师权限
        for (var icdCode : icdCodes) {
            // 检索所有有权限的手术
            var icd = this.icdMapper.selectById(icdCode);
            if (icd == null) {
                throw new RuntimeException(String.format("手术 %s 不存在", icdCode));
            }

            // 添加手术
            this.addPrivilege(icd, doctor);
        }
    }

    @Transactional
    public void addDoctPrivilege(String docCode, String deptCode, Integer level) {
        // 检索医生实体
        var doctor = this.emplMapper.selectById(docCode);
        if (doctor == null) {
            throw new RuntimeException("医师不存在");
        }

        // 检索科室实体
        var department = this.deptMapper.selectById(deptCode);
        if (department == null) {
            throw new RuntimeException("科室不存在");
        }

        // 检索指定手术
        var wrapper = new QueryWrapper<MetComIcdOperation>().lambda();
        wrapper.like(MetComIcdOperation::getDeptCode, department.getDeptCode());
        wrapper.eq(MetComIcdOperation::getSurgeryLevel, level);
        var icds = this.icdMapper.selectList(wrapper);

        // 轮训删除医师权限
        for (var icd : icds) {
            // 添加手术
            this.addPrivilege(icd, doctor);
        }
    }

    @Transactional
    public void addDeptPrivilege(String deptCode, List<String> icdCodes) {
        // 检索医生实体
        var department = this.deptMapper.selectById(deptCode);
        if (department == null) {
            throw new RuntimeException("科室不存在");
        }

        // 轮训删除科室权限
        for (var icdCode : icdCodes) {
            // 检索所有有权限的手术
            var icd = this.icdMapper.selectById(icdCode);
            if (icd == null) {
                throw new RuntimeException(String.format("手术 %s 不存在", icdCode));
            }

            // 添加手术
            this.addPrivilege(icd, department);
        }
    }
}
