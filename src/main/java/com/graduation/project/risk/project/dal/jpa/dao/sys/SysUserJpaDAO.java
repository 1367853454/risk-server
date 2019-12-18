package com.graduation.project.risk.project.dal.jpa.dao.sys;

import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysUserDO;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserJpaDAO extends JpaRepository<SysUserDO, Long> {

    SysUserDO findByAccountAndStatus(String account,CommonStatus status);

    SysUserDO findByAccount(String account);

    SysUserDO findByAccountAndFlag(String account,Integer flag);

}
