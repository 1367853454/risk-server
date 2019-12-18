package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.web.vo.rule.RuleVariableVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RuleVariableMapper {

    List<RuleVariableVO> variableSearch(@Param("variableName") String variableName, @Param("businessCode") String businessCode,
                                        @Param("variableType") String variableType, @Param("dataType") String dataType,
                                        @Param("variableDescribe") String variableDescribe);

    RuleVariableVO getRuleVariableInfo(Long variableId);
}
