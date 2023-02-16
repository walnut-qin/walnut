package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.api.data.enums.ValidStateEnum;

import lombok.Data;

@Data
@TableName("XYHIS.MET_COM_ICDOPERATION")
public class MetComIcdOperation {
    /**
     * ICD编码
     */
    @TableId("ICD_CODE")
    String icdCode;

    /**
     * 手术名
     */
    @TableField("ICD_NAME")
    String icdName;

    /**
     * 有效性
     */
    @TableField("VALID_STATE")
    ValidStateEnum validState;

    /**
     * 科室编码
     */
    @TableField("DEPT_CODE")
    String deptCode;

    /**
     * 手术名
     */
    @TableField("DEPT_NAME")
    String deptName;

    /**
     * 医生编码
     */
    @TableField("DOCT_CODE")
    String docCode;

    /**
     * 医生名
     */
    @TableField("DOCT_NAME")
    String docName;
}
