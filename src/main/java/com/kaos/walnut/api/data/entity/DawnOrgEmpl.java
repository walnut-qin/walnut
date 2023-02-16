package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.api.data.enums.ValidStateEnum;

import lombok.Data;

@Data
@TableName("XYHIS.DAWN_ORG_EMPL")
public class DawnOrgEmpl {
    /**
     * 科室编码
     */
    @TableId("EMPL_ID")
    String emplCode;

    /**
     * 手术名
     */
    @TableField("EMPL_NAME")
    String emplName;

    /**
     * 手术名
     */
    @TableField("VALID_STATE")
    ValidStateEnum validState;
}
