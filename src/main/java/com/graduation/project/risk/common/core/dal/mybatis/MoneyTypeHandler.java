package com.graduation.project.risk.common.core.dal.mybatis;

import com.graduation.project.risk.common.model.Money;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyTypeHandler implements TypeHandler<Money> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Money parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setString(i, null);
            return;
        }
        ps.setLong(i, parameter.getCent());

    }

    @Override
    public Money getResult(ResultSet resultSet, String columnName) throws SQLException {
        long cent = resultSet.getLong(columnName); // if NULL return 0
        return new Money(cent);
    }

    @Override
    public Money getResult(ResultSet resultSet, int i) throws SQLException {
        long cent = resultSet.getLong(i); // if NULL return 0
        return new Money(cent);
    }

    @Override
    public Money getResult(CallableStatement callableStatement, int i) throws SQLException {
        long cent = callableStatement.getLong(i); // if NULL return 0
        return new Money(cent);
    }
}
