package com.graduation.project.risk.project.biz.enums;

import com.graduation.project.risk.project.web.vo.KeyValueVO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum CommonRuleEnums {

    RULE_PACKAGE_DEFAULT_VERSION("1.0.0", "rule package default version"),//规则包初始化时默认版本号

    //规则执行条件
    allrule("allrule", "fit all condition"),//满足所有条件
    onerule("onerule", "fit the one condition"),//满足任意条件

    //变量处理（审核结果）
    PASS("PASS", "handle result is pass"),//通过
    REJECT("REJECT", "handle result is reject"),//拒绝
    REVIEW("REVIEW", "handle result is review"),//复议

    //规则执行类型
    SIMULATION("SIMULATION", "规则执行类型为模拟"),
    ORDINARY("ORDINARY", "规则执行类型为普通"),







    ;

    private String code;

    private String description;

    CommonRuleEnums(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CommonRuleEnums convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (CommonRuleEnums Ind : values()) {
            if (StringUtils.equals(Ind.getCode(), code)) {
                return Ind;
            }
        }
        return null;
    }

    public static List<KeyValueVO> list(){
        List<KeyValueVO> list = new ArrayList<>();
        for (CommonRuleEnums commonRuleEnums : values()){
            list.add(new KeyValueVO(commonRuleEnums.getCode(),commonRuleEnums.getDescription()));
        }
        return list;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
