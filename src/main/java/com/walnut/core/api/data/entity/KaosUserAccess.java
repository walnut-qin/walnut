package com.walnut.core.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.walnut.core.util.ObjectUtils;
import com.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("KAOS.KAOS_USER_ACCESS")
public class KaosUserAccess {
    /**
     * 编码
     */
    @TableId("USER_CODE")
    private String userCode;

    /**
     * 密码(密文)
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * token掩码，修改后旧token失效
     */
    @TableField("TOKEN_MASK")
    private String tokenMask;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof KaosUserAccess) {
            var that = (KaosUserAccess) arg0;
            return StringUtils.equals(this.userCode, that.userCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(userCode);
    }
}
