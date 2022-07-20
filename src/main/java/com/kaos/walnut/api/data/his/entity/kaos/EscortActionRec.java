package com.kaos.walnut.api.data.his.entity.kaos;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.type.Enum;

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
