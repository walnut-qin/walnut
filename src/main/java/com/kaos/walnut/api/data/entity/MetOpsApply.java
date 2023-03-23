package com.kaos.walnut.api.data.entity;

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
@TableName("XYHIS.MET_OPS_APPLY")
public class MetOpsApply {
    /**
     * 手术申请单号
     */
    @TableId("OPERATIONNO")
    String applyNo;

    /**
     * 住院号
     */
    @TableField("PATIENT_NO")
    String patientNo;

    /**
     * 手术类型
     */
    @TableField("OPS_KIND")
    OpsKindEnum opsKind;

    /**
     * 申请单状态
     */
    @TableField("EXECSTATUS")
    ExecStatusEnum execStatus;

    /**
     * 手术等级
     */
    @TableField("DEGREE")
    DegreeEnum degree;

    /**
     * 是否计划进入ICU
     */
    @TableField("ICU_FLAG")
    Boolean icuFlag;

    /**
     * 申请科室
     */
    @TableField("APPLY_DPCD")
    String applyDeptCode;

    /**
     * 拟手术时间
     */
    @TableField("PRE_DATE")
    LocalDateTime preDate;

    /**
     * 是否有效
     */
    @TableField("YNVALID")
    Boolean ynValid;

    /**
     * 是否有效
     */
    @TableField("ISCANCEL")
    Boolean isCancel;

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

    /**
     * 手术类型
     */
    @Getter
    @AllArgsConstructor
    public enum OpsKindEnum implements Enum {
        普通("1", "普通"),
        急诊("2", "急诊"),
        日间("3", "日间");

        /**
         * 数据库值
         */
        private String value;

        /**
         * 序列化值
         */
        private String description;
    }

    /**
     * 在院状态
     */
    @Getter
    @AllArgsConstructor
    public enum ExecStatusEnum implements Enum {
        手术申请("1", "手术申请"),
        手术审批("2", "手术审批"),
        手术安排("3", "手术安排"),
        手术完成("4", "手术完成"),
        取消手术等级("5", "取消手术等级"),
        手术审批未通过("6", "手术审批未通过");

        /**
         * 数据库值
         */
        private String value;

        /**
         * 序列化值
         */
        private String description;
    }

    /**
     * 在院状态
     */
    @Getter
    @AllArgsConstructor
    public enum DegreeEnum implements Enum {
        零级("0级", "0级"),
        一级("1级", "1级"),
        二级("2级", "2级"),
        三级("3级", "3级"),
        四级("4级", "4级");

        /**
         * 数据库值
         */
        private String value;

        /**
         * 序列化值
         */
        private String description;
    }
}
