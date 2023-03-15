package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.type.enums.SexEnum;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("XYHIS.COM_PATIENTINFO")
public class ComPatientInfo {
    /**
     * 就诊卡号
     */
    @TableId("CARD_NO")
    String cardNo;

    /**
     * 姓名
     */
    @TableField("NAME")
    String name;

    /**
     * 性别
     */
    @TableField("SEX_CODE")
    SexEnum sex;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof ComPatientInfo) {
            var that = (ComPatientInfo) arg0;
            return StringUtils.equals(this.cardNo, that.cardNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(cardNo);
    }
}
