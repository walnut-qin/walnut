package com.walnut.core.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.walnut.core.util.ObjectUtils;
import com.walnut.core.util.StringUtils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("KAOS.KAOS_USER_ROLE")
public class KaosUserRole {
    /**
     * 编码
     */
    @TableId("USER_CODE")
    private String userCode;

    /**
     * 角色
     */
    @TableId("ROLE")
    private String role;

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
