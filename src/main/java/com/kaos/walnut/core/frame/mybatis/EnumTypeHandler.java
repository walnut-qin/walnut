/*********************************************************
 * File: EnumTypeHandler.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  实现mybatis插件对枚举类型Enum<A, B>在读写数据库时的处理
 *  - 使用 Enum 的 A 域对接数据库
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.util.EnumUtils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class EnumTypeHandler<E extends Enum> extends BaseTypeHandler<E> {
    /**
     * E的class对象
     */
    private Class<E> classOfE;

    /**
     * 构造函数
     * 
     * @param typeOfE
     */
    public EnumTypeHandler(Class<E> classOfE) {
        this.classOfE = classOfE;
    }

    /**
     * 写数据库的操作函数
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    /**
     * 读数据库操作
     */
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getObject(columnName) == null) {
            return null;
        }
        return EnumUtils.fromValue(rs.getString(columnName), classOfE);
    }

    /**
     * 读数据库操作
     */
    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getObject(columnIndex) == null) {
            return null;
        }
        return EnumUtils.fromValue(rs.getString(columnIndex), classOfE);
    }

    /**
     * 读数据库操作
     */
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getObject(columnIndex) == null) {
            return null;
        }
        return EnumUtils.fromValue(cs.getString(columnIndex), classOfE);
    }
}
