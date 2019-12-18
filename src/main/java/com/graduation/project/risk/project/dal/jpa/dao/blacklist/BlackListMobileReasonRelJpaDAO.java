package com.graduation.project.risk.project.dal.jpa.dao.blacklist;

import com.graduation.project.risk.project.dal.jpa.dataobject.blacklist.BlackListMobileReasonRelDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListMobileReasonRelJpaDAO extends JpaRepository<BlackListMobileReasonRelDO,Long> {
}
