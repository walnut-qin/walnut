package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("KAOS_USER")
public class KaosUser {
    /**
     * 用户编码
     */
    @TableId("USER_CODE")
    String userCode;

    /**
     * 用户名称
     */
    @TableField("USER_NAME")
    String userName;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof KaosUser) {
            var that = (KaosUser) arg0;
            return StringUtils.equals(this.userCode, that.userCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(userCode);
    }
}
