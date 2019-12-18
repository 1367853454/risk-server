package com.graduation.project.risk.common.core.dal.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class EnumStringHandler<T> extends BaseTypeHandler<T> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if(parameter != null){
            ps.setString(i, getValue(parameter));
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String s) throws SQLException {
        String resultString = rs.getString(s);
        if (rs.wasNull()) {
            return null;
        } else {
            T t = fromValue(resultString);
            return t;
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String resultString = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            T t = fromValue(resultString);
            return t;
        }
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String resultString = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            T t = fromValue(resultString);
            return t;
        }
    }

    /***
     *
     * @param t
     * @return
     */
    protected abstract String getValue(T t);

    protected abstract T fromValue(String v);
}
