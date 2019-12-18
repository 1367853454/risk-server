package com.graduation.project.risk.common.base.config;

import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:springbeans-cache-config.xml"})
public class RedisConfig extends RedisAutoConfiguration {
}
