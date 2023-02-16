package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.api.data.enums.ValidStateEnum;

import lombok.Data;

@Data
@TableName("XYHIS.DAWN_ORG_DEPT")
public class DawnOrgDept {
    /**
     * 科室编码
     */
    @TableId("DEPT_ID")
    String deptCode;

    /**
     * 科室名
     */
    @TableField("DEPT_NAME")
    String deptName;

    /**
     * 有效性
     */
    @TableField("VALID_STATE")
    ValidStateEnum validState;
}
