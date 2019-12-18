package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.biz.enums.PublishStatus;
import com.graduation.project.risk.project.web.vo.rule.RulePackageVO;
import com.graduation.project.risk.project.web.vo.rule.RuleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RulePackageMapper {

    List<RulePackageVO> packageSearch(@Param("packageCode") String packageCode, @Param("packageName") String packageName,
                                      @Param("businessCode") String businessCode, @Param("flag") Integer flag);

    List<RuleVO> ruleInfo(@Param("packageCode")String packageCode, @Param("flag") Integer flag, @Param("version") String version);

    Integer beingUsed(@Param("packageId") Long packageId, @Param("flag") Integer flag, @Param("status")PublishStatus status);

    Integer release(@Param("businessCode") String businessCode, @Param("packageCode") String packageCode, @Param("flag") Integer flag);
}