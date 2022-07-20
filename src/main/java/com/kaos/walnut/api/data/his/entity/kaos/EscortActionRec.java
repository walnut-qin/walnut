package com.kaos.walnut.api.data.his.entity.kaos;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.util.IntegerUtils;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("KAOS.ESCORT_ACTION_REC")
public class EscortActionRec {
    /**
     * 陪护证号
     */
    @TableId("ESCORT_NO")
    @TableField("ESCORT_NO")
    String escortNo;

    /**
     * 操作序号
     */
    @TableId("REC_NO")
    @TableField("REC_NO")
    Integer recNo;

    /**
     * 陪护证状态
     */
    @TableField("ACTION")
    EscortActionEnum action;

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
        if (arg0 instanceof EscortActionRec) {
            var that = (EscortActionRec) arg0;
            return StringUtils.equals(this.escortNo, that.escortNo)
                    && IntegerUtils.equals(this.recNo, that.recNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(escortNo, recNo);
    }

    @Getter
    @AllArgsConstructor
    public enum EscortActionEnum implements Enum {
        进入("I", "进入"),
        外出("O", "外出");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }
}
