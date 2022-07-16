package com.kaos.walnut.api.data.docare.entity.medcomm;

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
@TableName("MEDCOMM.MED_ANAESTHESIA_DICT")
public class MedAnesthesiaDict {
    /**
     * 住院号
     */
    @TableId("ANAESTHESIA_NAME")
    String anesName;

    /**
     * 住院标识，门诊为0
     */
    @TableField("ANAESTHESIA_TYPE")
    AnesTypeEnum anesType;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MedAnesthesiaDict) {
            var that = (MedAnesthesiaDict) arg0;
            return StringUtils.equals(this.anesName, that.anesName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(anesName);
    }

    @Getter
    @AllArgsConstructor
    public enum AnesTypeEnum implements Enum {
        插管全麻("插管全麻", "插管全麻"),
        非插管全麻("非插管全麻", "非插管全麻"),
        复合麻醉("复合麻醉", "复合麻醉"),
        局麻("局麻", "局麻"),
        局麻强化MAC("局麻强化MAC", "局麻强化MAC"),
        神经阻滞("神经阻滞", "神经阻滞"),
        椎管内麻醉("椎管内麻醉", "椎管内麻醉");

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
