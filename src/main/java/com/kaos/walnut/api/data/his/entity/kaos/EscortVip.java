package com.kaos.walnut.api.data.his.entity.kaos;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.util.IntegerUtils;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("KAOS.ESCORT_VIP")
public class EscortVip {
    /**
     * 患者卡号
     */
    @TableId("PATIENT_CARD_NO")
    @TableField("PATIENT_CARD_NO")
    String patientCardNo;

    /**
     * 住院证序号
     */
    @TableId("HAPPEN_NO")
    @TableField("HAPPEN_NO")
    Integer happenNo;

    /**
     * 陪护人卡号
     */
    @TableField("HELPER_CARD_NO")
    String helperCardNo;

    /**
     * 操作日期
     */
    @TableField("OPER_DATE")
    LocalDateTime operDate;

    /**
     * 备注
     */
    @TableField("REMARK")
    String remark;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof EscortVip) {
            var that = (EscortVip) arg0;
            return StringUtils.equals(this.patientCardNo, that.patientCardNo)
                    && IntegerUtils.equals(this.happenNo, that.happenNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(patientCardNo, happenNo);
    }
}
