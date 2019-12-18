package com.graduation.project.risk.project.biz.manager;

import com.graduation.project.risk.project.dal.jpa.dao.rule.RuleBusinessJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RulePackageJpaDAO;
import com.graduation.project.risk.project.dal.mybaits.dao.RulePackageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleManager {

    private Logger logger = LoggerFactory.getLogger(RuleManager.class);

    @Autowired
    private RulePackageMapper rulePackageMapper;
    @Autowired
    private RuleBusinessJpaDAO ruleBusinessJpaDAO;
    @Autowired
    private RulePackageJpaDAO rulePackageJpaDAO;











}
