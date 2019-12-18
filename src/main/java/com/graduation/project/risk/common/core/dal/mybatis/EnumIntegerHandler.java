package com.graduation.project.risk.common.core.dal.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract  class EnumIntegerHandler<T> extends BaseTypeHandler<T> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if(parameter != null){
            ps.setInt(i,getValue(parameter));
        }
    }

    /***
     *
     * @param t
     * @return
     */
    protected abstract Integer getValue(T t);

    protected abstract T fromValue(Integer v);

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int resultString = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            T t = fromValue(resultString);
            return t;
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int resultString = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            T t = fromValue(resultString);
            return t;
        }
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int resultString = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            T t = fromValue(resultString);
            return t;
        }
    }
}
