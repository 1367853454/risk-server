package com.graduation.project.risk.project.dal.mybaits.dao;


import com.graduation.project.risk.project.dal.mybaits.dataobject.HomeStatisticsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HomeStatisticsMapper {

    List<HomeStatisticsDO> getStatisticsInfo(@Param("startDate") String startDate,
                                             @Param("stopDate") String stopDate,
                                             @Param("personProvince") String personProvince);

}
