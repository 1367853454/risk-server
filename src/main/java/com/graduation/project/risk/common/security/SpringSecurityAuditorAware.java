package com.graduation.project.risk.common.security;

import com.graduation.project.risk.common.constants.Constants;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentAppName();
        return (userName != null ? userName : Constants.SYSTEM_ACCOUNT);
    }
}
