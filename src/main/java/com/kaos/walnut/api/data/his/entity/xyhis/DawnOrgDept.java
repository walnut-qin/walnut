package com.kaos.walnut.api.data.his.entity.xyhis;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.api.data.his.enums.ValidEnum;
import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("XYHIS.DAWN_ORG_DEPT")
public class DawnOrgDept {
    /**
     * 科室编码
     */
    @TableId("DEPT_ID")
    private String deptCode;

    /**
     * 科室名称
     */
    @TableField("DEPT_NAME")
    private String deptName;

    /**
     * 有效标识
     */
    @TableField("VALID_STATE")
    private ValidEnum valid;

    /**
     * 科室类型
     */
    @TableField("DEPT_TYPE")
    private DeptTypeEnum deptType;

    /**
     * 院区
     */
    @TableField("DEPTOWN")
    private DeptOwnEnum deptOwn;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof DawnOrgDept) {
            var that = (DawnOrgDept) arg0;
            return StringUtils.equals(this.deptCode, that.deptCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(deptCode);
    }

    /**
     * 科室类型
     */
    @Getter
    @AllArgsConstructor
    public static enum DeptTypeEnum implements Enum {
        门诊("C", "门诊"),
        住院("I", "住院"),
        财务("F", "财务"),
        后勤("L", "后勤"),
        药库("PI", "药库"),
        医技("T", "医技"),
        其它("O", "其它"),
        机关("D", "机关"),
        药房("P", "药房"),
        护士站("N", "护士站"),
        OP("OP", "OP"),
        A0301("A03.01", "A03.01");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public enum DeptOwnEnum implements Enum {
        All("0", "全院区"),
        Sourth("1", "南院区"),
        North("2", "北院区"),
        East("3", "东津院区"),
        WanShan("4", "万山分院"),
        ZhongYuan("5", "中原社区");

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
