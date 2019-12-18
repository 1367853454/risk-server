package com.graduation.project.risk.common.core.dal.jpa;

import com.graduation.project.risk.common.core.dal.AbstractAuditingEntity;

import java.io.Serializable;
import java.util.Map;


public class BaseDO extends AbstractAuditingEntity {

    /**
     * extended information
     */
    protected transient Map<String, Serializable> links = new java.util.HashMap<>();

    public BaseDO link(String key, Serializable value) {

        links.put(key, value);

        return this;
    }

    public Map<String, Serializable> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Serializable> links) {
        this.links = links;
    }
}
