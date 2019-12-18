package com.graduation.project.risk.project.dal.jpa.dao.blacklist;

import com.graduation.project.risk.project.dal.jpa.dataobject.blacklist.BlackListRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRecordJpaDAO extends JpaRepository<BlackListRecordDO,Long> {
}
