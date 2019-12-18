package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.web.vo.system.SysUserSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

@Mapper
public interface SysUserMapper {

    List<SysUserSearchVO> userSearch(@Param("name") String name, @Param("status") String status);

}
