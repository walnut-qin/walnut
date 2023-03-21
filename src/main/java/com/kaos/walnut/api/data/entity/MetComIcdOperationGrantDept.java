package com.kaos.walnut.api.data.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("XYHIS.MET_COM_ICDOPERATION_GRANTDEPT")
public class MetComIcdOperationGrantDept {
    /**
     * ICD编码
     */
    @TableId("ICD_CODE")
    String icdCode;

    /**
     * 科室编码
     */
    @TableId("DEPT_ID")
    String deptId;

    /**
     * 操作员
     */
    @TableField("OPER_CODE")
    String operCode;

    /**
     * 操作时间
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
        if (arg0 instanceof MetComIcdOperationGrantDept) {
            var that = (MetComIcdOperationGrantDept) arg0;
            return StringUtils.equals(this.icdCode, that.icdCode)
                    && StringUtils.equals(this.deptId, that.deptId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(icdCode, deptId);
    }
}
