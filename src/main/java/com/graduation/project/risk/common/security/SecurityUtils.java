package com.graduation.project.risk.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.util.Collection;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    public static Long getCurrentAppId() {

        return getCurrentApp() == null ? null : Long.valueOf(getCurrentApp().getAppId());
    }

    public static String getCurrentAppName() {
        return getCurrentApp() == null ? null : getCurrentAppName();
    }

    /**
     * TODO
     * @return
     */
    public static SecurityApp getCurrentApp() {

        if (!isAuthenticated()) {
            return null;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        SecurityApp app = (SecurityApp) authentication.getPrincipal();

//        SecurityApp app = new SecurityApp();
//        app.setAppId("1");
//        app.setPartnerId("1");
//        app.setName("卡了吧唧");

        return app;

    }

    public static Serializable getCurrentAppProperty(String propertyName) {

        return getCurrentApp() == null ? null : getCurrentApp().getDetail(propertyName);
    }

    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities != null) {
                for (GrantedAuthority authority : authorities) {
                    if (authority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities != null) {
                for (GrantedAuthority grantedAuthority : authorities) {
                    if (grantedAuthority.getAuthority().equals(authority)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
