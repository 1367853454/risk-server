package com.graduation.project.risk.project.dal.mybaits.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PpSystemVariableInterfaceMapper {

    Integer applicationsWithSameDeviceIDOnTheSameDay(@Param("deviceId") String deviceId);

    Integer accountsLoggedInWithSameDeviceID(@Param("deviceId") String deviceId);

    Integer relevanceAmongUrgentContacts(@Param("contants") List<String> contants);

    Integer usersWithSameEmail(@Param("email") String email);

    Integer renewalApplicatioinsWithin24HoursAfterRepayment(@Param("orderCode") String orderCode, @Param("ktpNo") String ktpNo);

}
