package com.kaos.walnut.api.data.his.entity.kaos;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("KAOS.SURGERY_DEPT_PRIV")
public class SurgeryDeptPriv {
    /**
     * ICD编码
     */
    @TableId("ICD_CODE")
    @TableField("ICD_CODE")
    String icdCode;

    /**
     * 科室编码
     */
    @TableId("DEPT_CODE")
    @TableField("DEPT_CODE")
    String deptCode;

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
        if (arg0 instanceof SurgeryDeptPriv) {
            var that = (SurgeryDeptPriv) arg0;
            return StringUtils.equals(this.icdCode, that.icdCode)
                    && StringUtils.equals(this.deptCode, that.deptCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(icdCode, deptCode);
    }
}
