package com.kaos.walnut.api.logic.service.surgery.privilege;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaos.walnut.api.data.cache.DawnOrgDeptCache;
import com.kaos.walnut.api.data.cache.MetComIcdOperationCache;
import com.kaos.walnut.api.data.entity.MetComIcdOperationGrantDept;
import com.kaos.walnut.api.data.mapper.MetComIcdOperationGrantDeptMapper;
import com.kaos.walnut.core.frame.entity.User;

@Service
public class DeptGrantService {
    /**
     * 科室缓存
     */
    @Autowired
    DawnOrgDeptCache dawnOrgDeptCache;

    /**
     * 手术字典缓存
     */
    @Autowired
    MetComIcdOperationCache metComIcdOperationCache;

    /**
     * 科室授权接口
     */
    @Autowired
    MetComIcdOperationGrantDeptMapper metComIcdOperationGrantDeptMapper;

    /**
     * 单科室授权
     * 
     * @param icdCode
     * @param deptIcd
     */
    @Transactional
    public void grant(String icdCode, String deptId) {
        // 检索手术记录
        var icd = this.metComIcdOperationCache.get(icdCode);
        if (icd == null) {
            throw new RuntimeException("手术不存在-" + icdCode);
        }

        // 检索科室记录
        var dept = this.dawnOrgDeptCache.get(deptId);
        if (dept == null) {
            throw new RuntimeException("科室不存在-" + deptId);
        }

        // 检索授权记录
        var wrapper = new QueryWrapper<MetComIcdOperationGrantDept>().lambda();
        wrapper.eq(MetComIcdOperationGrantDept::getIcdCode, icdCode);
        wrapper.eq(MetComIcdOperationGrantDept::getDeptId, deptId);
        var grantRecord = this.metComIcdOperationGrantDeptMapper.selectOne(wrapper);
        if (grantRecord != null) {
            return;
        }

        // 授权
        grantRecord = new MetComIcdOperationGrantDept();
        grantRecord.setIcdCode(icdCode);
        grantRecord.setDeptId(deptId);
        grantRecord.setOperCode(User.currentUser().getUid());
        grantRecord.setOperDate(LocalDateTime.now());
        this.metComIcdOperationGrantDeptMapper.insert(grantRecord);
    }

    /**
     * 将某台手术授权给某个科室
     * 
     * @param icdCode
     * @param deptId
     */
    @Transactional
    public void grant(String icdCode, Collection<String> deptIds) {
        deptIds.forEach(deptId -> {
            this.grant(icdCode, deptId);
        });
    }

    /**
     * 给某个科室授权一批手术
     * 
     * @param deptId
     * @param icdCodes
     */
    @Transactional
    public void grant(Collection<String> icdCodes, String deptId) {
        icdCodes.forEach(icdCode -> {
            this.grant(icdCode, deptId);
        });
    }
}
