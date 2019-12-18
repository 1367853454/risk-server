package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.dal.jpa.dataobject.blacklist.BlackListIdCardDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.blacklist.BlackListMobileDO;
import com.graduation.project.risk.project.dal.mybaits.dataobject.BlackListSearchDO;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import com.graduation.project.risk.project.biz.enums.CommonType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlacklistMapper {

    void updateAddUserIntoBlacklist(BlackListIdCardDO blackListIdCardDO);

    List<BlackListSearchDO> getBlackListSearchDOByIdNumber(@Param("idNumber")String idNumber, @Param("status")CommonStatus status, @Param("type")CommonType type);

    List<BlackListSearchDO> getBlackListSearchDOByPhoneNumber(@Param("phoneNumber")String phoneNumber,@Param("status")CommonStatus status,@Param("type")CommonType type);

    List<BlackListMobileDO> getBlackListMobileDOByBlacklistIdcardId(@Param("blacklistIdcardId")Long blacklistIdcardId);

}
