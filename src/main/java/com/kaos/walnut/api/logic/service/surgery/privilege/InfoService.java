package com.kaos.walnut.api.logic.service.surgery.privilege;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaos.walnut.api.data.entity.MetComIcdOperation;
import com.kaos.walnut.api.data.entity.MetComIcdOperationGrantDept;
import com.kaos.walnut.api.data.entity.MetComIcdOperationGrantEmpl;
import com.kaos.walnut.api.data.mapper.MetComIcdOperationGrantDeptMapper;
import com.kaos.walnut.api.data.mapper.MetComIcdOperationGrantEmplMapper;
import com.kaos.walnut.api.data.mapper.MetComIcdOperationMapper;
import com.kaos.walnut.core.util.collection.CollectionUtils;

/**
 * 手术权限信息业务
 */
@Service
public class InfoService {
    /**
     * 手术字典接口
     */
    @Autowired
    MetComIcdOperationMapper metComIcdOperationMapper;

    /**
     * 科室授权接口
     */
    @Autowired
    MetComIcdOperationGrantDeptMapper metComIcdOperationGrantDeptMapper;

    /**
     * 医师授权接口
     */
    @Autowired
    MetComIcdOperationGrantEmplMapper metComIcdOperationGrantEmplMapper;

    /**
     * 获取被授权的手术
     * 
     * @param deptId 患者所在科室编码
     * @param emplId 拟手术医师编码
     * @return
     */
    @Transactional
    public List<MetComIcdOperation> getGrantedSurgeries(String deptId, String emplId) {
        // 获取科室被授权的手术
        var deptWrapper = new QueryWrapper<MetComIcdOperationGrantDept>().lambda();
        deptWrapper.eq(MetComIcdOperationGrantDept::getDeptId, deptId);
        var deptSurgeries = this.metComIcdOperationGrantDeptMapper.selectList(deptWrapper).stream().map(x -> {
            return x.getIcdCode();
        }).toList();

        // 获取医师被授权的手术
        var emplWrapper = new QueryWrapper<MetComIcdOperationGrantEmpl>().lambda();
        emplWrapper.eq(MetComIcdOperationGrantEmpl::getEmplId, emplId);
        var emplSurgeries = this.metComIcdOperationGrantEmplMapper.selectList(emplWrapper).stream().map(x -> {
            return x.getIcdCode();
        }).toList();

        // 获取交集
        var icds = CollectionUtils.intersection(deptSurgeries, emplSurgeries);

        // 映射为结果集
        var result = icds.stream().map(x -> {
            return this.metComIcdOperationMapper.selectById(x);
        }).filter(x -> {
            return x != null;
        }).toList();

        return result;
    }
}
