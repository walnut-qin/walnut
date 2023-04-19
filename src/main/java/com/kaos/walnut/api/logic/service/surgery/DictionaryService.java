package com.kaos.walnut.api.logic.service.surgery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kaos.walnut.api.data.entity.MetComIcdOperation;
import com.kaos.walnut.api.data.mapper.MetComIcdOperationMapper;
import com.kaos.walnut.core.util.IntegerUtils;

@Service
public class DictionaryService {
    /**
     * 字典接口
     */
    @Autowired
    MetComIcdOperationMapper metComIcdOperationMapper;

    /**
     * 修改手术等级
     * 
     * @param icdCode
     * @param level
     */
    @Transactional
    public void changeLevel(String icdCode, Integer level) {
        // 检索手术字典
        var icd = this.metComIcdOperationMapper.selectById(icdCode);
        if (icd == null) {
            throw new RuntimeException(String.format("手术不存在, icd = %s", icdCode));
        }

        // 若手术等级和目标相等，则不修改
        if (IntegerUtils.equals(icd.getSurgeryLevel(), level)) {
            return;
        }

        // 修改等级
        var wrapper = new UpdateWrapper<MetComIcdOperation>().lambda();
        wrapper.eq(MetComIcdOperation::getIcdCode, icdCode);
        wrapper.set(MetComIcdOperation::getSurgeryLevel, level);
        this.metComIcdOperationMapper.update(null, wrapper);
    }
}
