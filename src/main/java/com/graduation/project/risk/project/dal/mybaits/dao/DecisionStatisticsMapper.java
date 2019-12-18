package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.web.vo.decision.DecisionStatisticsFunnelVO;
import com.graduation.project.risk.project.web.vo.decision.DecisionStatisticsPackageVO;
import com.graduation.project.risk.project.web.vo.decision.DecisionStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DecisionStatisticsMapper {

    List<DecisionStatisticsVO> statistics(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("packageCode") String packageCode,
                                          @Param("flag") Integer flag, @Param("status")String status, @Param("processCode") String processCode);

    List<DecisionStatisticsPackageVO> packageVO(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("packageCode") String packageCode,
                                                @Param("status")String status, @Param("processCode") String processCode);

    List<DecisionStatisticsFunnelVO> funnel(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("packageCode") String packageCode,
                                            @Param("status")String status, @Param("processCode") String processCode);

}