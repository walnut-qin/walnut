package com.kaos.walnut.api.logic.service.surgery;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.kaos.walnut.api.data.entity.DawnOrgDept;
import com.kaos.walnut.api.data.entity.DawnOrgEmpl;
import com.kaos.walnut.api.data.entity.MetComIcdOperation;
import com.kaos.walnut.api.data.mapper.DawnOrgDeptMapper;
import com.kaos.walnut.api.data.mapper.DawnOrgEmplMapper;
import com.kaos.walnut.api.data.mapper.MetComIcdOperationMapper;
import com.kaos.walnut.core.util.StringUtils;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
