package com.graduation.project.risk.project.dal.jpa.dataobject.rule;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.common.core.biz.CommonUtil;
import com.graduation.project.risk.project.biz.enums.IfCanNull;
import com.graduation.project.risk.project.biz.enums.ScriptType;
import com.graduation.project.risk.project.biz.enums.VariableType;
import com.graduation.project.risk.project.web.form.rule.VariableAddForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "rule_variable")
@EqualsAndHashCode(callSuper = true)
public class RuleVariableDO extends BaseEntity {

    private String businessCode;

    private String businessName;

    private String variableName;

    private String variableDescribe;

    @Enumerated(EnumType.STRING)
    private VariableType variableType;

    private String dataType;

    @Enumerated(EnumType.STRING)
    private IfCanNull ifCanNull;

    private String defaultData;

    private Long systemVariableId;

    @Enumerated(EnumType.STRING)
    private ScriptType scriptType;

    private String schematic;

    private String title;

    private String description;

    private String scriptContent;


    public RuleVariableDO(VariableAddForm variableAddForm) {
        this.businessCode = variableAddForm.getBusinessCode();
        this.variableName = variableAddForm.getVariableName();
        this.variableDescribe = variableAddForm.getVariableDescribe();
        this.variableType = VariableType.convertFrom(variableAddForm.getVariableType());
        this.dataType = variableAddForm.getDataType();
        this.ifCanNull = IfCanNull.convertFrom(variableAddForm.getIfCanNull());
        this.defaultData = variableAddForm.getDefaultData();

        if (!CommonUtil.isEmpty(variableAddForm.getScriptType())){
            this.scriptType = ScriptType.convertFrom(variableAddForm.getScriptType());
        }
        if (!CommonUtil.isEmpty(variableAddForm.getSchematic())){
            this.schematic = variableAddForm.getSchematic();
        }
        if (!CommonUtil.isEmpty(variableAddForm.getTitle())){
            this.title = variableAddForm.getTitle();
        }
        if (!CommonUtil.isEmpty(variableAddForm.getDescription())){
            this.description= variableAddForm.getDescription();
        }
        //TODO---当变量类型选择DERIVATION时显示且必填，否则隐藏非必填
        if(!CommonUtil.isEmpty(variableAddForm.getScriptContent())){
            this.scriptContent = variableAddForm.getScriptContent();
        }

    }

    public RuleVariableDO() {
    }
}
