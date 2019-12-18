package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.web.vo.decision.RiskOrderVO;
import com.graduation.project.risk.project.biz.dto.RiskOrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RiskOrderMapper {

    List<RiskOrderVO> riskOrderSearch(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("dataType") String dateType);

    List<RiskOrderDTO> getOrderInfo(@Param("list") List<String> list);

}
