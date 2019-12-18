package com.graduation.project.risk.common.base.interceptor;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class MybatisInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(MybatisInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);

        long start = System.currentTimeMillis();
        Object returnValue = invocation.proceed();
        long end = System.currentTimeMillis();

        String sql = boundSql.getSql();

        String formatSql = sql.replace("\n", "").replaceAll("\\s+", " ");

        logger.info("exec sql: [{}]，use: [{}s]", formatSql, (end - start) / 1000f);

        return returnValue;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
