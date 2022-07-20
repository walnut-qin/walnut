package com.kaos.walnut.api.data.his.entity.kaos;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

public class EscortAnnexChk {
    /**
     * 附件
     */
    @TableId("ANNEX_NO")
    String annexNo;

    /**
     * 
     */
    @TableField("OPER_CODE")
    String operCode;

    /**
     * 
     */
    @TableField("OPER_DATE")
    LocalDateTime operDate;

    /**
     * 
     */
    @TableField("NEGATIVE")
    Boolean negative;

    /**
     * 
     */
    @TableField("EXEC_DATE")
    LocalDateTime execDate;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof EscortAnnexChk) {
            var that = (EscortAnnexChk) arg0;
            return StringUtils.equals(this.annexNo, that.annexNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(annexNo);
    }
}
