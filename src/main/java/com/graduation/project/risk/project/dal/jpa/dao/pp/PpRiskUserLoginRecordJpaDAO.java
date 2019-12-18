package com.graduation.project.risk.project.dal.jpa.dao.pp;

import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRiskUserLoginRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PpRiskUserLoginRecordJpaDAO extends JpaRepository<PpRiskUserLoginRecordDO,Long> {
}
