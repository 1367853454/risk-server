package com.graduation.project.risk.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class CommonRestResult<T> implements Serializable {
    public static final String FAIL = "fail";
    public static final String SUCCESS = "success";
    public static final String DEFAULT_SUCCESS_CODE = "000000";
    public static final String DEFAULT_ERROR_CODE = "999999";
    private String code;
    private T content;
    private Map metadata;
    private String status;
    private String message;
    private List<?> errors;

    public CommonRestResult fillMetaData(String key, Object value) {
        this.metadata.put(key, value);
        return this;
    }

    public CommonRestResult() {
        this.metadata = new HashMap();
        this.status = "success";
        this.errors = new ArrayList();
        this.code = "000000";
        this.metadata = new HashMap();
        this.status = "success";
        this.errors = new ArrayList();
    }

    public CommonRestResult(boolean success, String message, T content) {
        this.metadata = new HashMap();
        this.status = "success";
        this.errors = new ArrayList();
        this.code = "000000";
        this.metadata = new HashMap();
        this.status = "success";
        this.errors = new ArrayList();
        if (success) {
            this.status = "success";
            this.code = "000000";
        } else {
            this.status = "fail";
            this.code = "999999";
        }

        this.message = message;
        this.content = content;
    }

    public CommonRestResult(T content) {
        this();
        this.content = content;
    }

//    public CommonRestResult(String status, String message) {
//        this((Object)null);
//        this.status = status;
//        this.message = message;
//    }
//
//    public CommonRestResult(String status, String message, String code) {
//        this((Object)null);
//        this.status = status;
//        this.message = message;
//        this.code = code;
//    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getContent() {
        return this.content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Map getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Map metadata) {
        this.metadata = metadata;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean success() {
        return "success".equals(this.status);
    }

    public List<?> getErrors() {
        return this.errors;
    }

    public void setErrors(List<?> errors) {
        this.errors = errors;
    }
}

