package com.graduation.project.risk.project.web.vo.rule;

import lombok.Data;

@Data
public class RuleVariableVO {

    private Long variableId;

    private String businessName;

    private String variableName;

    private String systemName;

    private String variableDescribe;

    private String variableType;

    private String dataType;

    private String ifCanNull;

    private String defaultData;

    private Long systemVariableId;

    private String scriptType;

    private String schematic;

    private String title;

    private String description;

    private String scriptContent;

}
