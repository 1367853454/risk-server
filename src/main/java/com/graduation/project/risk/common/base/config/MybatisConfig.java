package com.graduation.project.risk.common.base.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.graduation.project.risk.common.core.dal.mybatis.BooleanTypeHander;
import com.graduation.project.risk.common.core.dal.mybatis.MoneyTypeHandler;
import com.graduation.project.risk.common.model.Money;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;

import com.github.pagehelper.PageHelper;

public class MybatisConfig {

    private static List<Class<?>> typeAliasClasses = new ArrayList<>();

    static {

//        typeAliasClasses.add(TestDO.class);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        Class[] typeAlias = new Class[typeAliasClasses.size()];
        typeAliasClasses.toArray(typeAlias);

        sqlSessionFactoryBean.setTypeAliases(typeAlias);
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

        SqlSessionFactory factory = sqlSessionFactoryBean.getObject();

        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        sqlSessionFactoryBean.setPlugins(new Interceptor[] { pageHelper });

        return factory;
    }

    @Bean
    public TypeHandlerRegistry typeHandlerRegistry(SqlSessionFactory sqlSessionFactory) {
        TypeHandlerRegistry thr = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        thr.register(Money.class, new MoneyTypeHandler());
        thr.register(Boolean.class, new BooleanTypeHander());
        return thr;
    }

    @Bean
    public MapperScannerConfigurer MapperScannerConfigurer(SqlSessionFactory sqlSessionFactory) {

        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setAnnotationClass(org.apache.ibatis.annotations.Mapper.class);
        configurer.setBasePackage("com.huijie.xcode.risk.project.dal.mybatis.dao");
        configurer.setSqlSessionFactory(sqlSessionFactory);

        return configurer;
    }
}
