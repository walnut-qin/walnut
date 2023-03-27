package com.kaos.walnut.api.logic.service.surgery.privilege;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaos.walnut.api.data.cache.DawnOrgEmplCache;
import com.kaos.walnut.api.data.cache.MetComIcdOperationCache;
import com.kaos.walnut.api.data.entity.MetComIcdOperationGrantEmpl;
import com.kaos.walnut.api.data.mapper.MetComIcdOperationGrantEmplMapper;
import com.kaos.walnut.core.frame.entity.User;

@Service
public class EmplGrantService {
    /**
     * 科室缓存
     */
    @Autowired
    DawnOrgEmplCache dawnOrgEmplCache;

    /**
     * 手术字典缓存
     */
    @Autowired
    MetComIcdOperationCache metComIcdOperationCache;

    /**
     * 科室授权接口
     */
    @Autowired
    MetComIcdOperationGrantEmplMapper metComIcdOperationGrantEmplMapper;

    /**
     * 单科室授权
     * 
     * @param icdCode
     * @param deptIcd
     */
    @Transactional
    public void grant(String icdCode, String emplId) {
        // 检索手术记录
        var icd = this.metComIcdOperationCache.get(icdCode);
        if (icd == null) {
            throw new RuntimeException("手术不存在-" + icdCode);
        }

        // 检索职工记录
        var dept = this.dawnOrgEmplCache.get(emplId);
        if (dept == null) {
            throw new RuntimeException("职工不存在-" + emplId);
        }

        // 检索授权记录
        var wrapper = new QueryWrapper<MetComIcdOperationGrantEmpl>().lambda();
        wrapper.eq(MetComIcdOperationGrantEmpl::getIcdCode, icdCode);
        wrapper.eq(MetComIcdOperationGrantEmpl::getEmplId, emplId);
        var grantRecord = this.metComIcdOperationGrantEmplMapper.selectOne(wrapper);
        if (grantRecord != null) {
            return;
        }

        // 授权
        grantRecord = new MetComIcdOperationGrantEmpl();
        grantRecord.setIcdCode(icdCode);
        grantRecord.setEmplId(emplId);
        grantRecord.setOperCode(User.currentUser().getUid());
        grantRecord.setOperDate(LocalDateTime.now());
        this.metComIcdOperationGrantEmplMapper.insert(grantRecord);
    }

    /**
     * 将某台手术授权给某个科室
     * 
     * @param icdCode
     * @param deptId
     */
    @Transactional
    public void grant(String icdCode, List<String> deptIds) {
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
    public void grant(List<String> icdCodes, String deptId) {
        icdCodes.forEach(icdCode -> {
            this.grant(icdCode, deptId);
        });
    }
}
