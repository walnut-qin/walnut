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

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import lombok.extern.log4j.Log4j2;

@Log4j2
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
     * 将源字符串对照枚举值域转换为枚举对象
     * 
     * @param source 源字符串
     * @return 枚举对象
     */
    private E getValue(String source) throws RuntimeException {
        // 轮训所有枚举对象，依次对照值域
        for (E e : classOfE.getEnumConstants()) {
            if (e.getValue().equals(source)) {
                return e;
            }
        }

        // 若对照失败，则写错误日志
        String err = String.format("反序列化枚举类型[%s]时, 出现了未对照的值[%s]", classOfE.getName(), source);
        log.error(err);
        throw new RuntimeException(err);
    }

    /**
     * 读数据库操作
     */
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getObject(columnName) == null) {
            return null;
        }
        return getValue(rs.getString(columnName));
    }

    /**
     * 读数据库操作
     */
    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getObject(columnIndex) == null) {
            return null;
        }
        return getValue(rs.getString(columnIndex));
    }

    /**
     * 读数据库操作
     */
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getObject(columnIndex) == null) {
            return null;
        }
        return getValue(cs.getString(columnIndex));
    }
}
