package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.web.vo.rule.RuleProcessPackageVO;
import com.graduation.project.risk.project.biz.enums.PublishStatus;
import com.graduation.project.risk.project.web.vo.rule.RuleProcessVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RuleProcessMapper {

    List<RuleProcessVO> processSearch(@Param("processName") String processName, @Param("businessCode") String businessCode, @Param("status")Integer status);

    List<RuleProcessPackageVO> packageSearch(@Param("processId") Long processId, @Param("flag") Integer flag , @Param("status") PublishStatus status);

}
