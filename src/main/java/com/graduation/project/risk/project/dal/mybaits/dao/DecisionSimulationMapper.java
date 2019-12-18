package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.web.vo.decision.DecisionSimulationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DecisionSimulationMapper {

    List<DecisionSimulationVO> processSearch(@Param("businessCode")String businessCode, @Param("processCode")String processCode, @Param("status")Integer status);

}
