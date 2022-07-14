package com.kaos.skynet.api.data.docare.entity.medsurgery;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.IntegerUtils;
import com.kaos.skynet.core.util.ObjectUtils;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("MEDSURGERY.MED_ANESTHESIA_PLAN")
public class MedAnesthesiaPlan {
    /**
     * 住院号
     */
    @TableId("PATIENT_ID")
    String patientId;

    /**
     * 住院标识，门诊为0
     */
    @TableId("VISIT_ID")
    Integer visitId;

    /**
     * 手术号
     */
    @TableId("OPER_ID")
    Integer operId;

    /**
     * ASA分级
     */
    @TableField("ASA_GRADE")
    AsaGradeEnum asaGrade;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MedAnesthesiaPlan) {
            var that = (MedAnesthesiaPlan) arg0;
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

    @Getter
    @AllArgsConstructor
    public enum AsaGradeEnum implements Enum {
        I("Ⅰ", "I"),
        II("Ⅱ", "II"),
        III("Ⅲ", "III"),
        IV("Ⅳ", "IV"),
        V("Ⅴ", "V"),
        VI("Ⅵ", "VI");

        /**
         * 数据库存值
         */
        String value;

        /**
         * 描述存值
         */
        String description;
    }
}
