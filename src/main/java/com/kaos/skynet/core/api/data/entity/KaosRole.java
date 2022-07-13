package com.kaos.skynet.core.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.skynet.core.util.ObjectUtils;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("KAOS.KAOS_ROLE")
public class KaosRole {
    /**
     * 键值
     */
    @TableId("KEY")
    private String key;

    /**
     * 描述
     */
    @TableField("VALUE")
    private String value;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof KaosRole) {
            var that = (KaosRole) arg0;
            return StringUtils.equals(this.key, that.key);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(key);
    }
}
