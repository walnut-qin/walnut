package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kaos.walnut.core.util.IntegerUtils;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
public class MedAnesthesiaEvent {
    /**
     * 住院号
     */
    @TableId("PATIENT_ID")
    String patientId;

    /**
     * 看诊次数
     */
    @TableId("VISIT_ID")
    Integer visitId;

    /**
     * 手术次序
     */
    @TableId("OPER_ID")
    Integer operId;

    /**
     * 项目序号
     */
    @TableId("ITEM_NO")
    Integer itemNo;

    /**
     * 项目序号
     */
    @TableField("ITEM_CLASS")
    String itemClass;

    /**
     * 事件序号
     */
    @TableId("EVENT_NO")
    Integer eventNo;

    /**
     * 项目序号
     */
    @TableField("ITEM_NAME")
    String itemName;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MedAnesthesiaEvent) {
            var that = (MedAnesthesiaEvent) arg0;
            return StringUtils.equals(this.patientId, that.patientId)
                    && IntegerUtils.equals(this.visitId, that.visitId)
                    && IntegerUtils.equals(this.operId, that.operId)
                    && IntegerUtils.equals(this.itemNo, that.itemNo)
                    && IntegerUtils.equals(this.eventNo, that.eventNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(patientId, visitId, operId, itemNo, eventNo);
    }
}
