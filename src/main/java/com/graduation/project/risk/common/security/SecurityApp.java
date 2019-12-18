package com.graduation.project.risk.common.security;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.*;

public class SecurityApp {

    /**
     *
     */
    private String                                 appId;

    /**
     *
     */
    private String                                 partnerId;

    /**
     *
     */
    private String                                 name;


    /**
     * user's authorities
     */
    private Collection<? extends GrantedAuthority> authorities = new HashSet<>();

    private Map<String, Serializable>              detail      = new HashMap<>();

    public SecurityApp() {

    }

    public SecurityApp(String appId, String partnerId, String name, Collection<? extends GrantedAuthority> authorities, Map<String, Serializable> detail) {
        this.appId = appId;
        this.partnerId = partnerId;
        this.name = name;
        this.authorities = authorities;
        this.detail = detail;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Serializable getDetail(String key) {
        return detail.get(key);
    }

    public Serializable get(String key) {
        return getDetail(key);
    }

    public Map<String, Serializable> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, Serializable> detail) {
        this.detail = detail;
    }
}
