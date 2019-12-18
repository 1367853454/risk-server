package com.graduation.project.risk.common.base.config;

import com.graduation.project.risk.common.base.aop.logging.LoggingAspect;
import com.graduation.project.risk.common.constants.Constants;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
