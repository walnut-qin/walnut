package com.kaos.walnut.api.data.his.entity.kaos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("KAOS.CONFIG_LIST")
public class ConfigList {
    /**
     * 名
     */
    @TableId("NAME")
    @TableField("NAME")
    String name;

    /**
     * 值
     */
    @TableId("VALUE")
    @TableField("VALUE")
    String value;

    /**
     * 有效性
     */
    @TableField("VALID")
    Boolean valid;

    /**
     * 备注
     */
    @TableField("REMARK")
    String remark;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof ConfigList) {
            var that = (ConfigList) arg0;
            return StringUtils.equals(this.name, that.name)
                    && StringUtils.equals(this.value, that.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(name, value);
    }
}
