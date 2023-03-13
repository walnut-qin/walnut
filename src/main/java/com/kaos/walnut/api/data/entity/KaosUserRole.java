package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

/**
 * 用户角色
 */
@Data
@TableName("KAOS.KAOS_USER_ROLE")
public class KaosUserRole {
    /**
     * 用户编码
     */
    @TableId("USER_CODE")
    String userCode;

    /**
     * 用户名称
     */
    @TableId("ROLE")
    String role;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof KaosUserRole) {
            var that = (KaosUserRole) arg0;
            return StringUtils.equals(this.userCode, that.userCode)
                    && StringUtils.equals(this.role, that.role);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(userCode, role);
    }
}
