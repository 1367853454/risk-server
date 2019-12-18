package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.web.vo.system.RoleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {

    List<RoleVO> getSysRole(Long Userid);

}
