package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("XYHIS.FIN_IPB_DAYREPORTDETAIL")
public class FinIpbDayReportDetail {
    /**
     * 日结序号
     */
    @TableId("STAT_NO")
    String statNo;

    /**
     * 日结时间
     */
    @TableId("STAT_NO")
    String statCode;

    /**
     * 日结时间
     */
    @TableField("TOT_COST")
    String totCost;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIpbDayReport) {
            var that = (FinIpbDayReport) arg0;
            return StringUtils.equals(this.statNo, that.statNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(statNo);
    }
}
