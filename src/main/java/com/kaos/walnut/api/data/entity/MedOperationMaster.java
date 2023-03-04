package com.kaos.walnut.api.data.entity;

import java.time.LocalDateTime;

import com.kaos.walnut.core.util.IntegerUtils;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
public class MedOperationMaster {
    /**
     * 住院号
     */
    String patientId;

    /**
     * 看诊次数
     */
    Integer visitId;

    /**
     * 手术次序
     */
    Integer operId;

    /**
     * 入复苏室时间
     */
    LocalDateTime inPacuDateTime;

    /**
     * 出复苏室时间
     */
    LocalDateTime outPacuDateTime;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MedOperationMaster) {
            var that = (MedOperationMaster) arg0;
            return StringUtils.equals(this.patientId, that.patientId)
                    && IntegerUtils.equals(this.visitId, that.visitId)
                    && IntegerUtils.equals(this.operId, that.operId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(patientId, visitId, operId);
    }
}
