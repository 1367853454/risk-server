package com.graduation.project.risk.project.dal.jpa.dao;

import com.graduation.project.risk.project.dal.jpa.dataobject.LoginRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRecordJpaDAO extends JpaRepository<LoginRecordDO,Long> {
}
