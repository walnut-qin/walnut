package com.kaos.walnut.api.data.his.entity.kaos;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("KAOS.SURGERY_DICT")
public class SurgeryDict {
    /**
     * ICD编码
     */
    @TableId("ICD_CODE")
    String icdCode;

    /**
     * 手术名称
     */
    @TableField("SURGERY_NAME")
    String surgeryName;

    /**
     * 手术等级
     */
    @TableField("SURGERY_LEVEL")
    SurgeryLevelEnum surgeryLevel;

    /**
     * 手术等级
     */
    @TableField("VALID")
    Boolean valid;

    /**
     * 操作时间
     */
    @TableField("OPER_DATE")
    LocalDateTime operDate;

    /**
     * 提词器
     */
    @TableField("TELEPROMPTER")
    String teleprompter;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof SurgeryDict) {
            var that = (SurgeryDict) arg0;
            return StringUtils.equals(this.icdCode, that.icdCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(icdCode);
    }

    @Getter
    @AllArgsConstructor
    public enum SurgeryLevelEnum implements Enum {
        一级("1", "一级"),
        二级("2", "二级"),
        三级("3", "三级"),
        四级("4", "四级");

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
