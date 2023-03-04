package com.kaos.walnut.api.data.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kaos.walnut.core.util.IntegerUtils;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
public class MedOperationMaster {
    /**
     * 住院号
     */
    @TableId("PATIENT_ID")
    String patientId;

    /**
     * 看诊次数
     */
    @TableId("VISIT_ID")
    Integer visitId;

    /**
     * 手术次序
     */
    @TableId("OPER_ID")
    Integer operId;

    /**
     * 手术结束时间
     */
    @TableField("END_DATE_TIME")
    LocalDateTime endDateTime;

    /**
     * 入复苏室时间
     */
    @TableField("IN_PACU_DATE_TIME")
    LocalDateTime inPacuDateTime;

    /**
     * 出复苏室时间
     */
    @TableField("OUT_PACU_DATE_TIME")
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
