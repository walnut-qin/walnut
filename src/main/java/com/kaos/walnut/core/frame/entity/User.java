/*********************************************************
 * File: User.java
 * Created Date: 2023-02-08
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  系统用户实体
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.entity;

import lombok.Data;

@Data
public class User {
    /**
     * 用户的全局唯一标识
     */
    private String uid = null;

    /**
     * 用户姓名
     */
    private String name = null;

    /**
     * 用户线程变量
     */
    final static ThreadLocal<User> threadUser = new ThreadLocal<>();

    /**
     * 创建线程变量
     * 
     * @param user
     */
    public static void create(User user) {
        threadUser.set(user);
    }

    /**
     * 销毁线程变量
     */
    public static void destroy() {
        threadUser.remove();
    }

    /**
     * 获取当前用户
     * 
     * @return
     */
    public static User currentUser() {
        return threadUser.get();
    }
}
