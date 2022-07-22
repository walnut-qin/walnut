package com.walnut.core.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.walnut.core.util.ObjectUtils;
import com.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("KAOS.KAOS_ETERNAL_TOKEN")
public class KaosEternalToken {
    /**
     * 永久token
     */
    @TableId("ETERNAL_TOKEN")
    String eternalToken;

    /**
     * 用户名
     */
    @TableField("USER_CODE")
    String userCode;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof KaosEternalToken) {
            var that = (KaosEternalToken) arg0;
            return StringUtils.equals(this.eternalToken, that.eternalToken);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(eternalToken);
    }
}
