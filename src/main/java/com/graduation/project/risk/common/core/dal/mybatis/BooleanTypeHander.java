package com.graduation.project.risk.common.core.dal.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanTypeHander implements TypeHandler<Boolean> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Boolean aBoolean, JdbcType jdbcType) throws SQLException {

        preparedStatement.setInt(i,((aBoolean)?1:0));
    }

    @Override
    public Boolean getResult(ResultSet resultSet, String s) throws SQLException {
        Integer cent = resultSet.getInt(s);
        return (cent > 0);
    }

    @Override
    public Boolean getResult(ResultSet resultSet, int i) throws SQLException {
        long cent = resultSet.getInt(i); // if NULL return 0
        return (cent > 0);
    }

    @Override
    public Boolean getResult(CallableStatement callableStatement, int i) throws SQLException {
        Integer cent = callableStatement.getInt(i); // if NULL return 0
        return (cent > 0);
    }
}
