package com.graduation.project.risk.project.dal.jpa.dao.blacklist;

import com.graduation.project.risk.project.dal.jpa.dataobject.blacklist.BlackListIdCardReasonRelDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BlackListIdCardReasonRelJpaDAO extends JpaRepository<BlackListIdCardReasonRelDO,Long> {

}
