package com.kaos.skynet.api.data.docare.mapper.medsurgery;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaos.skynet.api.data.docare.entity.medsurgery.MedAnesthesiaPlan;

@DS("docare")
public interface MedAnesthesiaPlanMapper extends BaseMapper<MedAnesthesiaPlan> {
    /**
     * 主键查询
     * 
     * @param patientId
     * @param visitId
     * @param oprId
     * @return
     */
    MedAnesthesiaPlan selectByMultiId(String patientId, Integer visitId, Integer operId);
}
