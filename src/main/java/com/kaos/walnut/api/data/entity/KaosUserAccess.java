package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("KAOS_USER_ACCESS")
public class KaosUserAccess {
    /**
     * 用户编码
     */
    @TableId("USER_CODE")
    String userCode;

    /**
     * 用户名称
     */
    @TableField("PASSWORD")
    String password;

    /**
     * token掩码
     */
    @TableField("TOKEN_MASK")
    String tokenMask;
}
