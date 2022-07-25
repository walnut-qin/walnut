package com.kaos.walnut.api.data.his.mapper.kaos;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaos.walnut.api.data.his.entity.kaos.EscortMainInfo;

@DS("his")
public interface EscortMainInfoMapper extends BaseMapper<EscortMainInfo> {
    /**
     * 查询某患者某状态的陪护
     * 
     * @param patientCardNo 患者卡号
     * @param valid         是否有效
     * @return
     */
    List<EscortMainInfo> selectByPatient(String cardNo, Boolean valid);

    /**
     * 查询某陪护某状态的陪护
     * 
     * @param patientCardNo 患者卡号
     * @param valid         是否有效
     * @return
     */
    List<EscortMainInfo> selectByHelper(String cardNo, Boolean valid);
}
