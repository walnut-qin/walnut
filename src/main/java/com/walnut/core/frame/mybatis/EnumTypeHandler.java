package com.walnut.core.frame.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.walnut.core.type.Enum;

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

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    /**
     * 将字符串转为枚举类型
     * 
     * @param source
     * @return
     */
    private E getValue(String source) {
        for (E e : classOfE.getEnumConstants()) {
            if (e.getValue().equals(source)) {
                return e;
            }
        }
        String err = String.format("反序列化枚举类型[%s]时, 出现了未对照的值[%s]", classOfE.getName(), source);
        log.warn(err);
        throw new RuntimeException(err);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getObject(columnName) == null) {
            return null;
        }
        return getValue(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getObject(columnIndex) == null) {
            return null;
        }
        return getValue(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getObject(columnIndex) == null) {
            return null;
        }
        return getValue(cs.getString(columnIndex));
    }
}
