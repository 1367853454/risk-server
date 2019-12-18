package com.graduation.project.risk.common.base.config;

import com.graduation.project.risk.common.core.web.filter.CommonRequestDataFilter;
import com.graduation.project.risk.common.core.web.filter.VersionControlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class CustomerFilterConfiguration {

    @Bean
    public FilterRegistrationBean CommonsRequestLoggingFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CommonsRequestLoggingFilter());
        registration.addUrlPatterns("/*");
        registration.setName("commonsRequestLoggingFilter");
        registration.setEnabled(true);
        registration.setOrder(1);
        return registration;
    }


    @Bean
    public FilterRegistrationBean CommonRequestDataFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CommonRequestDataFilter());
        registration.addUrlPatterns("/*");
        registration.setName("commonRequestDataFilter");
        registration.setEnabled(true);
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public FilterRegistrationBean VersionControlFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new VersionControlFilter());
        registration.addUrlPatterns("/*");
        registration.setName("versionControlFilter");
        registration.setEnabled(true);
        registration.setOrder(3);
        return registration;
    }



}
