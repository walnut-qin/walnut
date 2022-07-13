package com.kaos.skynet.core.api.data.entity;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.skynet.core.type.enums.SexEnum;
import com.kaos.skynet.core.util.ObjectUtils;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("KAOS.KAOS_USER")
public class KaosUser {
    /**
     * 编码
     */
    @TableId("USER_CODE")
    private String userCode;

    /**
     * 姓名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 身份证号
     */
    @TableField("IDENTITY_NO")
    private String identityNo;

    /**
     * 生日
     */
    @TableField("BIRTHDAY")
    private LocalDate birthday;

    /**
     * 性别
     */
    @TableField("SEX")
    private SexEnum sex;

    /**
     * 邮件
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 电话
     */
    @TableField("TELEPHONE")
    private String telephone;

    /**
     * 头像
     */
    @TableField("AVATAR")
    private byte[] avatar;

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

    /**
     * 线程变量
     */
    final static ThreadLocal<KaosUser> kaosUser = new ThreadLocal<>();

    /**
     * 创建用户实体
     */
    public static void create(KaosUser newUser) {
        kaosUser.set(newUser);
    }

    /**
     * 销毁用户实体
     */
    public static void destroy() {
        kaosUser.remove();
    }

    /**
     * 获取当前登入的用户
     * 
     * @return
     */
    public static KaosUser getCurrentUser() {
        return kaosUser.get();
    }
}
