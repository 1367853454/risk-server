package com.graduation.project.risk.project.biz.manager;

import com.graduation.project.risk.common.core.biz.CommonUtil;
import com.graduation.project.risk.integration.RedisService;
import com.graduation.project.risk.project.biz.constants.CommonConstant;
import com.graduation.project.risk.project.dal.jpa.dao.pp.PpRiskLoanResultJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RuleExcuteRecordJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRiskLoanResultDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleExcuteRecordDO;
import com.graduation.project.risk.project.biz.enums.BusinessType;
import com.graduation.project.risk.project.biz.enums.CommonRuleEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

@Service
public class DroolsRuleManager {

    private Logger logger = LoggerFactory.getLogger(DroolsRuleManager.class);

    @Autowired
    private RuleExcuteRecordJpaDAO ruleExcuteRecordJpaDAO;

    @Autowired
    private PpRiskLoanResultJpaDAO ppRiskLoanResultJpaDAO;

    @Autowired
    private RedisService redisService;


    public void recordSimulationExcuteResult(Map<String, Object> map) {
        logger.error("====recordSimulationExcuteResult.map===" + map);

        String orderCode = (String) map.get(CommonConstant.ORDER_CODE);

        map.remove(CommonConstant.ORDER_CODE);
        map.remove(CommonConstant.PROCESS_CODE);
        map.remove(CommonConstant.BUSINESS_CODE);

        TreeSet<String> resultList = new TreeSet<>();
        if (CommonUtil.isEmpty(map)) {
            redisService.set(orderCode + CommonRuleEnums.SIMULATION.getCode(), CommonRuleEnums.PASS.getCode(), -1);
        } else {

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                //TODO--暂时只处理规则执行只有一个结果，不考虑多个结果情况
                String value = (String) entry.getValue();
                String[] values = value.split(",");
                resultList.add(values[values.length - 1]);
            }

            if (!CommonUtil.isEmpty(resultList)) {
                for (String s : resultList) {
                    if (CommonRuleEnums.REJECT.getCode().equals(s)) {
                        redisService.set(orderCode + CommonRuleEnums.SIMULATION.getCode(), CommonRuleEnums.REJECT.getCode(), -1);
                        break;
                    }
                    if (CommonRuleEnums.REVIEW.getCode().equals(s)) {
                        redisService.set(orderCode + CommonRuleEnums.SIMULATION.getCode(), CommonRuleEnums.REVIEW.getCode(), -1);
                    } else {
                        redisService.set(orderCode + CommonRuleEnums.SIMULATION.getCode(), CommonRuleEnums.PASS.getCode(), -1);
                    }
                }
            }
        }
    }


    /**
     * drools记录操作结果的方法，**不要改方法名字**
     *
     * @param map
     */
    public void recordExcuteResult(Map<String, Object> map) {
        logger.error("====recordExcuteResult.map===" + map);

        List<RuleExcuteRecordDO> ruleExcuteRecordDOList = new ArrayList<>();

        String orderCode = (String) map.get(CommonConstant.ORDER_CODE);
        String processCode = (String) map.get(CommonConstant.PROCESS_CODE);
        String businessCode = (String) map.get(CommonConstant.BUSINESS_CODE);

        map.remove(CommonConstant.ORDER_CODE);
        map.remove(CommonConstant.PROCESS_CODE);
        map.remove(CommonConstant.BUSINESS_CODE);

        TreeSet<String> resultList = new TreeSet<>();

        if (CommonUtil.isEmpty(map)) {//没有命中任何规则，则PASS
            PpRiskLoanResultDO ppRiskLoanResultDO = new PpRiskLoanResultDO();
            ppRiskLoanResultDO.setOrderCode(orderCode);
            ppRiskLoanResultDO.setResult(CommonRuleEnums.PASS);
            ppRiskLoanResultJpaDAO.save(ppRiskLoanResultDO);
        } else {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                RuleExcuteRecordDO ruleExcuteRecordDO = new RuleExcuteRecordDO(businessCode, processCode, orderCode);
                ruleExcuteRecordDO.setRuleId(Long.valueOf(entry.getKey()));
                //TODO--暂时只处理规则执行只有一个结果，不考虑多个结果情况
                String value = (String) entry.getValue();
                String[] values = value.split(",");
                ruleExcuteRecordDO.setPackageCode(values[0]);
                ruleExcuteRecordDO.setResult(values[values.length - 1]);

                ruleExcuteRecordDOList.add(ruleExcuteRecordDO);
                resultList.add(values[values.length - 1]);
            }

            ruleExcuteRecordJpaDAO.save(ruleExcuteRecordDOList);

            //TODO-处理pp
            if (BusinessType.pp.getCode().equals(businessCode)) {
                if (!CommonUtil.isEmpty(resultList)) {
                    PpRiskLoanResultDO ppRiskLoanResultDO = new PpRiskLoanResultDO();
                    ppRiskLoanResultDO.setOrderCode(orderCode);
                    for (String s : resultList) {
                        if (CommonRuleEnums.REJECT.getCode().equals(s)) {
                            ppRiskLoanResultDO.setResult(CommonRuleEnums.REJECT);
                            break;
                        }
                        if (CommonRuleEnums.REVIEW.getCode().equals(s)) {
                            ppRiskLoanResultDO.setResult(CommonRuleEnums.REVIEW);
                        } else {
                            ppRiskLoanResultDO.setResult(CommonRuleEnums.PASS);
                        }
                    }
                    ppRiskLoanResultJpaDAO.save(ppRiskLoanResultDO);
                }
            }
        }
    }
}
