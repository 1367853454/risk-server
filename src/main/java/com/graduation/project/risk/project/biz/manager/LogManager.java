package com.graduation.project.risk.project.biz.manager;

import com.graduation.project.risk.project.dal.jpa.dao.LoginRecordJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RuleRecordJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dataobject.LoginRecordDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleRecordDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysUserDO;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogManager {

    private Logger logger = LoggerFactory.getLogger(LogManager.class);

    @Autowired
    RuleRecordJpaDAO ruleRecordJpaDAO;

    @Autowired
    LoginRecordJpaDAO loginRecordJpaDAO;

    public void variableLog(Long ruleVariableId, String operatorType){

        try {
            RuleRecordDO log = new RuleRecordDO();
            log.setRuleVariableId(ruleVariableId);
            log.setOperatorId(((SysUserDO) SecurityUtils.getSubject().getPrincipal()).getId());
            log.setOperatorType(operatorType);
            log.setOperatorTime(new Date());
            ruleRecordJpaDAO.save(log);
        }
        catch (Exception e){

        }

    }

    public void loginLog(){

        try {
            LoginRecordDO log = new LoginRecordDO();
            log.setSysUserId(((SysUserDO) SecurityUtils.getSubject().getPrincipal()).getId());
            log.setSysUserAccount(((SysUserDO) SecurityUtils.getSubject().getPrincipal()).getAccount());
            log.setLoginTime(new Date());
            loginRecordJpaDAO.save(log);
        }
        catch (Exception e){

        }

    }

}
