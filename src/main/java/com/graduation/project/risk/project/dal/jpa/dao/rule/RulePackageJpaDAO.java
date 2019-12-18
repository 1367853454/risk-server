package com.graduation.project.risk.project.dal.jpa.dao.rule;

import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RulePackageDO;
import com.graduation.project.risk.project.biz.enums.PublishStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RulePackageJpaDAO extends JpaRepository<RulePackageDO,Long> {
    List<RulePackageDO> findByBusinessCodeAndPackageCodeAndFlag(String businessCode,String packageCode,Integer flag);

    List<RulePackageDO> findByPackageCodeAndFlag(String packageCode, Integer flag);

    RulePackageDO findByIdAndFlag(Long id,Integer flag);

    RulePackageDO findByPackageCodeAndPackageVersionAndFlag(String packageCode,String packageVersion,Integer flag);

    RulePackageDO findByBusinessCodeAndAndPackageCodeAndPublishStatusAndFlag(String businessCode, String packageCode, PublishStatus publishStatus,Integer flag);

    RulePackageDO findById(Long id);

    RulePackageDO findByPackageCodeAndFlagAndPublishStatus(String packageCode, Integer flag, PublishStatus status);

    RulePackageDO findByBusinessCodeAndPackageCodeAndPackageVersionAndFlag(String businessCode, String packageCode, String packageVersion, Integer flag);

    List<RulePackageDO> findAllByFlag(Integer flag);

}
