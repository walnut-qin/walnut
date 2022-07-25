package com.kaos.walnut.api.data.his.entity.xyhis;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.api.data.his.enums.ValidEnum;
import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.type.enums.SexEnum;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("XYHIS.DAWN_ORG_EMPL")
public class DawnOrgEmpl {
    /**
     * 工号
     */
    @TableId("EMPL_ID")
    private String emplCode;

    /**
     * 职工姓名
     */
    @TableField("EMPL_NAME")
    private String emplName;

    /**
     * 有效标识
     */
    @TableField("VALID_STATE")
    private ValidEnum valid;

    /**
     * 姓名拼音码
     */
    @TableField("SPELL_CODE")
    private String spellCode;

    /**
     * 性别
     */
    @TableField("SEX_CODE")
    private SexEnum sex;

    /**
     * 生日
     */
    @TableField("BIRTHDAY")
    private LocalDateTime birthday;

    /**
     * 职务
     */
    @TableField("POSI_CODE")
    private PositionEnum position;

    /**
     * 职级
     */
    @TableField("LEVL_CODE")
    private RankEnum rank;

    /**
     * 人员类型
     */
    @TableField("EMPL_TYPE")
    private EmplTypeEnum emplType;

    /**
     * 身份证号
     */
    @TableField("IDENNO")
    private String idenNo;

    /**
     * 归属科室编码
     */
    @TableField("DEPT_ID")
    private String deptCode;

    /**
     * 归属护士站编码
     */
    @TableField("NURSE_CELL_ID")
    private String nurseCellCode;

    /**
     * 电子邮件
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 电话号码
     */
    @TableField("TEL")
    private String tel;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof DawnOrgEmpl) {
            var that = (DawnOrgEmpl) arg0;
            return StringUtils.equals(this.emplCode, that.emplCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(emplCode);
    }

    /**
     * 职务
     */
    @Getter
    @AllArgsConstructor
    public static enum PositionEnum implements Enum {
        院长("1", "院长"),
        主任("2", "主任"),
        科长("3", "科长"),
        科员("4", "科员");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 职级
     */
    @Getter
    @AllArgsConstructor
    public static enum RankEnum implements Enum {
        特殊津贴专家("1", "特殊津贴专家"),
        主任医师("2", "主任医师"),
        副主任医师("3", "副主任医师"),
        主治医师("4", "主治医师"),
        医师("5", "医师"),
        见习医师("6", "见习医师"),
        副主任护师("7", "副主任护师"),
        主管护师("8", "主管护师"),
        护师("9", "护师");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 职工类型
     */
    @Getter
    @AllArgsConstructor
    public static enum EmplTypeEnum implements Enum {
        所有("A", "所有"),
        厨师("C", "厨师"),
        医生("D", "医生"),
        收款员("F", "收款员"),
        护士("N", "护士"),
        其他("O", "其他"),
        药师("P", "药师"),
        技师("T", "技师");

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
