package com.graduation.project.risk.common.base.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = "com.graduation.project.risk.project.dal.jpa.dao")
@EntityScan(basePackages = "com.graduation.project.risk.project.dal.jpa.dataobject")
public class JpaConfig {

}
