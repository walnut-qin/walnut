package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.api.data.enums.ValidStateEnum;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("XYHIS.DAWN_ORG_DEPT")
public class DawnOrgDept {
    /**
     * 科室编码
     */
    @TableId("DEPT_ID")
    String deptCode;

    /**
     * 科室名
     */
    @TableField("DEPT_NAME")
    String deptName;

    /**
     * 有效性
     */
    @TableField("VALID_STATE")
    ValidStateEnum validState;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof DawnOrgDept) {
            var that = (DawnOrgDept) arg0;
            return StringUtils.equals(this.deptCode, that.deptCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(deptCode);
    }
}
