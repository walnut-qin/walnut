package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.api.data.enums.ValidStateEnum;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("XYHIS.DAWN_ORG_EMPL")
public class DawnOrgEmpl {
    /**
     * 科室编码
     */
    @TableId("EMPL_ID")
    String emplCode;

    /**
     * 手术名
     */
    @TableField("EMPL_NAME")
    String emplName;

    /**
     * 有效性
     */
    @TableField("VALID_STATE")
    ValidStateEnum validState;

    /**
     * 归属科室
     */
    @TableField("DEPT_ID")
    String deptCode;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof DawnOrgEmpl) {
            var that = (DawnOrgEmpl) arg0;
            return StringUtils.equals(this.emplCode, that.emplCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(emplCode);
    }
}
