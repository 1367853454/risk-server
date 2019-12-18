package com.graduation.project.risk.common.core.dal.mongdb;

import com.graduation.project.risk.common.core.dal.AbstractAuditingEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ApiModel(parent = AbstractAuditingEntity.class)
public class BaseEntity extends AbstractAuditingEntity {

    @Id
    @ApiModelProperty(value = "ID")
    protected String                    id;

    protected String                    keyword;

    protected Map<String, Serializable> extinfo = new HashMap<>();

    @Transient
    protected Map<String, Object>       links   = new HashMap<>();

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public BaseEntity link(String key, Object value) {
        links.put(key, value);
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Map<String, Object> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Object> links) {
        this.links = links;
    }

    public Map<String, Serializable> getExtinfo() {
        return extinfo;
    }

    public void setExtinfo(Map<String, Serializable> extinfo) {
        this.extinfo = extinfo;
    }
}
