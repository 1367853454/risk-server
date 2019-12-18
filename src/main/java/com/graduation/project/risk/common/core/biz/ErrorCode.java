package com.graduation.project.risk.common.core.biz;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCode {

    /**
     * common
     */

    SYNC("sync", "sync"),

    ASYNC("async", "async"),

    SUCCESS("000000", "成功"),

    MISSING_PARAMETER("000001", "参数缺失"),

    TOKEN_PARSE_FAILED("000002", "token解析失败"),

    UNKNOWN_REQUEST_APP("000003", "无效的请求token"),

    SYSTEM_EXCEPTION("999999", "系统错误"),

    /**
     * blk
     */
    MOBILE_EXISTED("MOBILE_EXISTED", "MOBILE_EXISTED"),
    IDCARD_EXISTED("IDCARD_EXISTED", "IDCARD_EXISTED"),
    MOBILE_0R_IDCARD_EXISTED("MOBILE_0R_IDCARD_EXISTED", "MOBILE_0R_IDCARD_EXISTED"),

    NOT_EXIST_BY_PARAMS("NOT_EXIST_BY_PARAMS", "NOT_EXIST_BY_PARAMS"),
    NOT_INTERNAL_BLACKLIST_ID("NOT_INTERNAL_BLACKLIST_ID","NOT_INTERNAL_BLACKLIST_ID"),
    EXISTING_IDCARD_NAME_NOT_MATCH_INCOMING_IDCARD_NAME("EXISTING_IDCARD_NAME_NOT_MATCH_INCOMING_IDCARD_NAME","EXISTING_IDCARD_NAME_NOT_MATCH_INCOMING_IDCARD_NAME"),

    USER_NOT_EXISTED("USER_NOT_EXISTED","USER_NOT_EXISTED"),
    USER_NOT_EXISTED_BY_THIS_STATUS("USER_NOT_EXISTED_BY_THIS_STATUS","USER_NOT_EXISTED_BY_THIS_STATUS"),
    USER_EXISTED("USER_EXISTED","USER_EXISTED"),
    ROLE_NAME_EXISTED("ROLE_NAME_EXISTED","ROLE_NAME_EXISTED"),
    ROLE_NAME_NOT_EXISTED("ROLE_NAME_NOT_EXISTED","ROLE_NAME_NOT_EXISTED"),
    PASSWORD_ERROR("PASSWORD_ERROR","PASSWORD_ERROR"),

    PARENT_ID_LESS_THAN_ZERO("PARENT_ID_LESS_THAN_ZERO","PARENT_ID_LESS_THAN_ZERO"),
    PERMISSION_REPETITION("PERMISSION_REPETITION","PERMISSION_REPETITION"),

    PARAM_MISS("param miss", "param miss"),
    NOT_SUPPORT_METHOD("10002", "not support method"),
    SEND_FAILED("send failed", "send failed"),
    PACKAGE_CODE_NOT_EXISTED("PACKAGE_CODE_NOT_EXISTED","PACKAGE_CODE_NOT_EXISTED"),

    NO_RECORD("NO_RECORD","NO_RECORD"),





    /**
     * rule
     */
    BUSINESS_CODE_NOT_EXISTED("business code not existed", "business code not existed"),
    DEFAULT_IS_NULL("default is null", "default is null"),
    SYSTEM_VARIABLE_NOT_EXISTED("system variable not existed", "system variable not existed"),
    VARIABLE_NAMW_IS_EXISTED("variable name is existed", "variable name is existed"),
    PACKAGE_CODE_IS_EXISTED("package code is existed", "package code is existed"),
    PACKAGE_INFO_NOT_EXISTED("package info not existed", "package info not existed"),
    PACKAGE_IS_PUBLISH("package is published", "package is published"),
    RULE_PACKAGE_ONLINE("RULE_PACKAGE_ONLINE", "RULE_PACKAGE_ONLINE"),
    RULE_HANDLE_OR_CONDITION_MISS("RULE_HANDLE_OR_CONDITION_MISS","RULE_HANDLE_OR_CONDITION_MISS"),
    PROCESS_IS_EXISTED("process is existed", "process is existed"),
    PROCESS_INFO_NOT_EXISTED("PROCESS_INFO_NOT_EXISTED","PROCESS_INFO_NOT_EXISTED"),
    PROCESS_IS_PUBLISH("PROCESS_IS_PUBLISH","PROCESS_IS_PUBLISH"),
    PROCESS_ID_ERROR("PROCESS_ID_ERROR","PROCESS_ID_ERROR"),
    PROCESS_IS_NOT_EXISTED("process is not existed", "process is not existed"),
    PACKAGE_BEING_USED("PACKAGE_BEING_USED","PACKAGE_BEING_USED"),




    /*
    *pp
     */
    NO_REPAYMENT_HAS_YET_BEEN_MADE("NO_REPAYMENT_HAS_YET_BEEN_MADE","NO_REPAYMENT_HAS_YET_BEEN_MADE"),


    TIME_IS_TOO_LONG("TIME_IS_TOO_LONG","TIME_IS_TOO_LONG"),
    /**
     * drools
     */
    DROOLS_EXECUTE_FAIL("drools execute fail", "drools execute fail"),//规则执行失败
    DROOLS_GENERATE_FAIL("drools generate fail", "drools generate fail"),//规则组装失败
    DROOLS_INIT_FAIL("drools init fail", "drools init fail"),

    ;


    private String code;

    private String desc;

    ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ErrorCode convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (ErrorCode type : values()) {
            if (StringUtils.equals(code, type.getCode())) {
                return type;
            }
        }
        return null;
    }

    /**
     * 导出错误码
     *
     * @param args
     */
    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>();
        for (ErrorCode code : values()) {
            map.put(code.getCode(), code.getDesc());
        }

        System.out.println(JSON.toJSONString(map));
    }
}
