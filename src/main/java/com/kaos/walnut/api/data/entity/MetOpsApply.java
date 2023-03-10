package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("XYHIS.MET_OPS_APPLY")
public class MetOpsApply {
    /**
     * 手术申请单号
     */
    @TableId("OPERATIONNO")
    String applyNo;

    /**
     * 是否计划进入ICU
     */
    @TableField("ICU_FLAG")
    Boolean icuFlag;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MetOpsApply) {
            var that = (MetOpsApply) arg0;
            return StringUtils.equals(this.applyNo, that.applyNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(applyNo);
    }
}
