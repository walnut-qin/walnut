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
@TableName("XYHIS.FIN_IPR_INMAININFO")
public class FinIprInMainInfo {
    /**
     * 住院流水号
     */
    @TableId("INPATIENT_NO")
    String inpatientNo;

    /**
     * 住院号
     */
    @TableField("PATIENT_NO")
    String patientNo;

    /**
     * 就诊卡号
     */
    @TableField("CARD_NO")
    String cardNo;

    /**
     * 住院科室
     */
    @TableField("DEPT_CODE")
    String deptCode;

    /**
     * 病区号
     */
    @TableField("NURSE_CELL_CODE")
    String nurseCellCode;

    /**
     * 床号
     */
    @TableField("BED_NO")
    String bedNo;

    /**
     * 住院医师编码
     */
    @TableField("HOUSE_DOC_CODE")
    String houseDocCode;

    /**
     * 上级医师编码
     */
    @TableField("CHARGE_DOC_CODE")
    String chargeDocCode;

    /**
     * 主任医师编码
     */
    @TableField("CHIEF_DOC_CODE")
    String chiefDocCode;

    /**
     * 责任护士编码
     */
    @TableField("DUTY_NURSE_CODE")
    String dutyNurseCode;

    /**
     * 责任护士编码
     */
    @TableField("IN_STATE")
    InStateEnum inState;

    /**
     * 责任护士编码
     */
    @TableField("IN_DATE")
    LocalDateTime inDate;

    /**
     * 责任护士编码
     */
    @TableField("OUT_DATE")
    LocalDateTime outDate;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIprInMainInfo) {
            var that = (FinIprInMainInfo) arg0;
            return StringUtils.equals(this.inpatientNo, that.inpatientNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(inpatientNo);
    }

    /**
     * 在院状态
     */
    @Getter
    @AllArgsConstructor
    public enum InStateEnum implements Enum {
        住院登记("R", "住院登记"),
        病房接诊("I", "病房接诊"),
        出院登记("B", "出院登记"),
        出院结算("O", "出院结算"),
        预约出院("P", "预约出院"),
        无非退院("N", "无非退院");

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
