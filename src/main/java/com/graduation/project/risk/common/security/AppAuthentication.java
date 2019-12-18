package com.graduation.project.risk.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AppAuthentication implements Authentication {

    protected final SecurityApp app;

    protected boolean            authenticated;

    /**
     *
     * @param app
     */
    public AppAuthentication(SecurityApp app) {
        this.app = app;
        this.authenticated = true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return app.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getDetails() {
        return app;
    }

    @Override
    public Object getPrincipal() {
        return app;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    @Override
    public String getName() {
        return app.getName();

    }

    public String getAppId() {
        return app.getAppId();
    }

    public String getPartnerId() {
        return app.getPartnerId();
    }

}
