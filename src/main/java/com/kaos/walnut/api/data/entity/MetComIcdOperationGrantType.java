package com.kaos.walnut.api.data.entity;

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
@TableName("XYHIS.MET_COM_ICDOPERATION_GRANTTYPE")
public class MetComIcdOperationGrantType {
    /**
     * ICD编码
     */
    @TableId("CODE")
    String code;

    /**
     * 科室编码
     */
    @TableField("NAME")
    String name;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MetComIcdOperationGrantType) {
            var that = (MetComIcdOperationGrantType) arg0;
            return StringUtils.equals(this.code, that.code);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(code);
    }

    @Getter
    @AllArgsConstructor
    public enum GrantTypeEnum implements Enum {
        普通手术("1", "普通手术"),
        日间手术("2", "日间手术");

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
