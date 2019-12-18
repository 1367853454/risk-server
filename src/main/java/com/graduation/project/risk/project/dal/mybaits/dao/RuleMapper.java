package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.web.vo.rule.RuleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RuleMapper {

    List<RuleVO> conditionInfo (@Param("id") Long id);

    List<RuleVO> handleinfo(@Param("id") Long id);
}
