package com.kaos.walnut.api.data.his.entity.kaos;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("KAOS.SURGERY_EMPL_PRIV")
public class SurgeryEmplPriv {
    /**
     * ICD编码
     */
    @TableId("ICD_CODE")
    @TableField("ICD_CODE")
    String icdCode;

    /**
     * 职工编码
     */
    @TableId("EMPL_CODE")
    @TableField("EMPL_CODE")
    String emplCode;

    /**
     * 有效性
     */
    @TableField("VALID")
    Boolean valid;

    /**
     * 操作日期
     */
    @TableField("OPER_DATE")
    LocalDateTime operDate;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof SurgeryEmplPriv) {
            var that = (SurgeryEmplPriv) arg0;
            return StringUtils.equals(this.icdCode, that.icdCode)
                    && StringUtils.equals(this.emplCode, that.emplCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(icdCode, emplCode);
    }
}
