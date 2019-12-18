package com.graduation.project.risk.project.biz.manager;

import com.graduation.project.risk.common.core.biz.BizCoreException;
import com.graduation.project.risk.project.biz.dto.RuleFactParams;
import com.graduation.project.risk.project.biz.util.DroolsUtil;
import com.graduation.project.risk.project.dal.jpa.dao.rule.*;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.*;
import com.graduation.project.risk.common.core.biz.CommonUtil;
import com.graduation.project.risk.common.core.biz.ErrorCode;
import com.graduation.project.risk.project.biz.constants.CommonConstant;
import com.graduation.project.risk.project.biz.enums.BusinessType;
import com.graduation.project.risk.project.biz.enums.CommonRuleEnums;
import com.graduation.project.risk.project.biz.enums.PublishStatus;
import com.graduation.project.risk.project.biz.enums.TriggerLinkEnums;
import com.graduation.project.risk.project.dal.jpa.dao.pp.PpRuleSystemVariableJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRuleSystemVariableDO;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RuleDroolsManager {

    private Logger logger = LoggerFactory.getLogger(RuleDroolsManager.class);

    @Autowired
    private RuleJpaDAO ruleJpaDAO;

    @Autowired
    private RuleConditionJpaDAO ruleConditionJpaDAO;

    @Autowired
    private RuleHandleJpaDAO ruleHandleJpaDAO;

    @Autowired
    private RulePackageJpaDAO rulePackageJpaDAO;

    @Autowired
    private RuleProcessJpaDAO ruleProcessJpaDAO;

    @Autowired
    private RuleProcessPackageRelJpaDAO ruleProcessPackageRelJpaDAO;

    @Autowired
    private RuleVariableJpaDAO ruleVariableJpaDAO;

    @Autowired
    private PpRuleSystemVariableJpaDAO ppRuleSystemVariableJpaDAO;

    @Autowired
    private DroolsRuleManager droolsRuleManager;


    /**
     * 先查缓存，再执行规则引擎
     *
     * @param ruleFactParams
     * @param processCode
     */
    public void excute(CommonRuleEnums commonRuleEnums, String businessCode, RuleFactParams ruleFactParams, final String processCode, TriggerLinkEnums triggerLink) {
        try {
            KieSession ksession = null;
            if (CommonRuleEnums.SIMULATION==commonRuleEnums){//规则模拟使用
                ksession = DroolsUtil.getInstance().getDrlSessionInCache(processCode+CommonRuleEnums.SIMULATION.getCode());
            }
            if (CommonRuleEnums.ORDINARY == commonRuleEnums){//正常规则使用
                ksession = DroolsUtil.getInstance().getDrlSessionInCache(processCode);
            }

            if (CommonUtil.isEmpty(ksession)) {
                logger.error("ksession为空，先编译，再执行规则");
                //重新编译规则
                StringBuilder droolRuleStr = compileRule(commonRuleEnums,businessCode, processCode, triggerLink);
//                if (!DroolsUtil.getInstance().compileRule(droolRuleStr.toString()+"333")){//校验规则
//                    logger.error("droolRuleStr----droolRuleStr is error------>>>>>>>>" + droolRuleStr);
//                }
                logger.error("droolRuleStr:"+droolRuleStr);
                compileRuleAndexEcuteRuleEngine(commonRuleEnums,droolRuleStr, ruleFactParams, processCode);
            } else {
                logger.error("ksession不为空，直接执行规则");
                //执行规则
                executeRuleEngine(ksession, ruleFactParams);
            }
        } catch (Exception e) {
            logger.error("规则引擎执行失败:", e);
            throw new BizCoreException(ErrorCode.DROOLS_EXECUTE_FAIL);
        }
    }


    /**
     * 执行规则引擎
     *
     * @param session
     * @param ruleFactParams
     */
    private void executeRuleEngine(KieSession session, RuleFactParams ruleFactParams) {
        try {
            // 插入全局对象
            Map<String, Object> globalMap = ruleFactParams.getGlobalMap();
            for (Object glb : globalMap.entrySet()) {
                Map.Entry global = (Map.Entry) glb;
                String key = (String) global.getKey();
                Object value = global.getValue();
                logger.error("插入Global对象:{}" + value.getClass().getName());
                session.setGlobal(key, value);
            }

            // 插入业务fact对象
            List<Object> factObjectList = ruleFactParams.getFactObjectList();
            for (Object o : factObjectList) {
                logger.error("插入Fact对象：{}" + o.getClass().getName());
                session.insert(o);
            }

            // 规则执行完记录执行结果manager
            session.insert(droolsRuleManager);

            //执行规则
            session.fireAllRules();
        } catch (Exception e) {
            logger.error("规则引擎执行出错:", e);
            throw new BizCoreException(ErrorCode.DROOLS_EXECUTE_FAIL);
        } finally {
            //释放资源
            session.dispose();
        }
    }


    /**
     * 规则加入缓存中
     *
     * @param droolRuleStr
     * @param ruleFactParams
     * @param processCode
     */
    private void compileRuleAndexEcuteRuleEngine(CommonRuleEnums commonRuleEnums,StringBuilder droolRuleStr, RuleFactParams ruleFactParams, String processCode) {
        //KieSession对象
        KieSession session = null;
        try {
            //编译规则脚本,返回KieSession对象
            if (CommonRuleEnums.SIMULATION==commonRuleEnums){
                session = DroolsUtil.getInstance().getDrlSession(droolRuleStr.toString(), processCode+CommonRuleEnums.SIMULATION.getCode());
            }
            if (CommonRuleEnums.ORDINARY==commonRuleEnums){
                session = DroolsUtil.getInstance().getDrlSession(droolRuleStr.toString(), processCode);
            }
        } catch (Exception e) {
            throw new BizCoreException(ErrorCode.DROOLS_INIT_FAIL);
        }
        //执行规则
        this.executeRuleEngine(session, ruleFactParams);
    }


    /**
     * 拼接drools语句
     * @param commonRuleEnums
     * @param businessCode
     * @param processCode
     * @param triggerLink
     * @return
     */
    public StringBuilder compileRule(CommonRuleEnums commonRuleEnums,String businessCode, String processCode, TriggerLinkEnums triggerLink) {
        StringBuilder droolRuleStr = new StringBuilder();

        droolRuleStr.append("package com.huijie.xcode.drools.").append(processCode + ";").append(CommonConstant.LINE_SEPARATOR);

        droolRuleStr.append("import com.huijie.xcode.risk.project.biz.dto.RuleFactResult").append(";").append(CommonConstant.LINE_SEPARATOR);
        droolRuleStr.append("import com.huijie.xcode.risk.project.biz.dto.RuleFactParams").append(";").append(CommonConstant.LINE_SEPARATOR);
        droolRuleStr.append("import java.lang.String").append(";").append(CommonConstant.LINE_SEPARATOR);
        droolRuleStr.append("import java.util.Map").append(";").append(CommonConstant.LINE_SEPARATOR);
        droolRuleStr.append("import java.util.List").append(";").append(CommonConstant.LINE_SEPARATOR);
        droolRuleStr.append("import java.util.HashMap").append(";").append(CommonConstant.LINE_SEPARATOR);
        droolRuleStr.append("import com.huijie.xcode.risk.project.biz.manager.DroolsRuleManager").append(";").append(CommonConstant.LINE_SEPARATOR);

        droolRuleStr.append("global RuleFactResult _result").append(";").append(CommonConstant.LINE_SEPARATOR);

        //处理pp
        if (BusinessType.pp.getCode().equals(businessCode)) {
            droolRuleStr.append("import com.huijie.xcode.risk.project.dal.jpa.dataobject.pp.PpSystemVariableBO").append(";").append(CommonConstant.LINE_SEPARATOR).append(CommonConstant.LINE_SEPARATOR);

            //查询pp业务的系统变量，放map里
            Map<Long, String> systemVariableMap = new HashMap<>();
            List<PpRuleSystemVariableDO> ppRuleSystemVariableDOS = ppRuleSystemVariableJpaDAO.findAll();
            if (!CommonUtil.isEmpty(ppRuleSystemVariableDOS)) {
                for (PpRuleSystemVariableDO ppRuleSystemVariableDO : ppRuleSystemVariableDOS) {
                    systemVariableMap.put(ppRuleSystemVariableDO.getId(), ppRuleSystemVariableDO.getVariableName());
                }
            }

            //组装规则
            RuleProcessDO ruleProcessDO = ruleProcessJpaDAO.findByBusinessCodeAndProcessCodeAndTriggerLinkAndPublishStatusAndFlag(
                    businessCode, processCode, triggerLink, PublishStatus.published, CommonConstant.NORMAL_FLAG);
            if (CommonUtil.isEmpty(ruleProcessDO)) {
                logger.error("compileRule.未找到可用的规则流程");
                throw new BizCoreException(ErrorCode.DROOLS_GENERATE_FAIL);
            }

            Long ruleProcessId = ruleProcessDO.getId();
            List<RuleProcessPackageRelDO> ruleProcessPackageRelDOS = ruleProcessPackageRelJpaDAO.findByRuleProcessIdAndFlagOrderByRulePackageOrderedNumber(ruleProcessId, CommonConstant.NORMAL_FLAG);
            if (CommonUtil.isEmpty(ruleProcessPackageRelDOS)) {
                logger.error("compileRule.通过规则流程id未找到规则包");
                throw new BizCoreException(ErrorCode.DROOLS_GENERATE_FAIL);
            }
            for (RuleProcessPackageRelDO ruleProcessPackageRelDO : ruleProcessPackageRelDOS) {
                String rulePackageCode = ruleProcessPackageRelDO.getRulePackageCode();
                //通过规则包找到规则（发布状态的规则包）
                RulePackageDO rulePackageDO = rulePackageJpaDAO.findByBusinessCodeAndAndPackageCodeAndPublishStatusAndFlag(businessCode, rulePackageCode, PublishStatus.published, CommonConstant.NORMAL_FLAG);
                if (CommonUtil.isEmpty(rulePackageDO)) {
                    logger.error("compileRule.通过业务和规则包代码，未找到发布状态的规则包版本");
                    throw new BizCoreException(ErrorCode.DROOLS_GENERATE_FAIL);
                }
                Long rulePackageId = rulePackageDO.getId();
                List<RuleDO> ruleDOS = ruleJpaDAO.findByRulePackageIdAndStatusAndFlag(rulePackageId, PublishStatus.published, CommonConstant.NORMAL_FLAG);
                if (CommonUtil.isEmpty(ruleDOS)) {
                    logger.error("compileRule.未找到有效的规则");
                    throw new BizCoreException(ErrorCode.DROOLS_GENERATE_FAIL);
                }
                for (RuleDO ruleDO : ruleDOS) {
                    Long ruleId = ruleDO.getId();
                    String ruleName = ruleDO.getRuleName();
                    CommonRuleEnums executeCondition = ruleDO.getExecuteCondition();//规则执行方式：全部满足；满足一个
                    List<RuleConditionDO> ruleConditionDOS = ruleConditionJpaDAO.findByRuleId(ruleId);
                    if (CommonUtil.isEmpty(ruleConditionDOS)) {
                        logger.error("compileRule.未找到规则下的判断条件,ruleId:" + ruleId);
                        throw new BizCoreException(ErrorCode.DROOLS_GENERATE_FAIL);
                    }
                    droolRuleStr.append(CommonConstant.DROOLS_RULE).append(" ").append("\"").append(ruleName + ruleId).append("\"").append(CommonConstant.LINE_SEPARATOR);
                    droolRuleStr.append(CommonConstant.DROOLS_WHEN).append(CommonConstant.LINE_SEPARATOR);

                    droolRuleStr.append("PpSystemVariableBO").append("(");
                    for (int i = 0; i < ruleConditionDOS.size(); i++) {

                        RuleVariableDO ruleVariableDO = ruleVariableJpaDAO.findById(ruleConditionDOS.get(i).getRuleVariableId());
                        Long systemVariableId = ruleVariableDO.getSystemVariableId();
                        String variableName = systemVariableMap.get(systemVariableId);

                        droolRuleStr.append(variableName).append(ruleConditionDOS.get(i).getCompare()).append(ruleConditionDOS.get(i).getCompareValue());

                        //满足规则中的所有条件
                        if (executeCondition == CommonRuleEnums.allrule && (i < ruleConditionDOS.size() - 1)) {
                            droolRuleStr.append(" ").append(CommonConstant.DROOLS_AND).append(" ");
                        }
                        //满足规则中的一个条件
                        if (executeCondition == CommonRuleEnums.onerule && (i < ruleConditionDOS.size() - 1)) {
                            droolRuleStr.append(" ").append(CommonConstant.DROOLS_OR).append(" ");
                        }
                    }
                    droolRuleStr.append(")").append(CommonConstant.LINE_SEPARATOR);
                    droolRuleStr.append(CommonConstant.DROOLS_THEN).append(CommonConstant.LINE_SEPARATOR);

                    //处理执行结果
                    List<RuleHandleDO> ruleHandleDOS = ruleHandleJpaDAO.findByRuleId(ruleId);
                    if (CommonUtil.isEmpty(ruleHandleDOS)) {
                        logger.error("compileRule.未找到规则下的条件执行结果操作,ruleId:" + ruleId);
                        throw new BizCoreException(ErrorCode.DROOLS_GENERATE_FAIL);
                    }

                    for (int i = 0; i < ruleHandleDOS.size(); i++) {
                        RuleVariableDO ruleVariableDO = ruleVariableJpaDAO.findById(ruleHandleDOS.get(i).getRuleVariableId());
                        Long systemVariableId = ruleVariableDO.getSystemVariableId();
                        String variableName = systemVariableMap.get(systemVariableId);

                        droolRuleStr.append("_result.getMap().put").append("(").append("\"").append(ruleId).append("\"").append(",").append("\"").append(rulePackageCode).append(",")
                                .append(variableName).append(",").append(ruleHandleDOS.get(i).getCompare()).append(",").append(ruleHandleDOS.get(i).getCompareValue()).append("\"").append(");").append(CommonConstant.LINE_SEPARATOR);
                    }
                    droolRuleStr.append(CommonConstant.DROOLS_END).append(CommonConstant.LINE_SEPARATOR).append(CommonConstant.LINE_SEPARATOR);
                }
            }
            //最后增加结果记录的操作
            droolRuleStr.append(CommonConstant.DROOLS_RULE).append(" ").append("\"").append("ruleRecord").append("\"").append(CommonConstant.LINE_SEPARATOR);
            droolRuleStr.append(CommonConstant.DROOLS_SALIENCE).append(" ").append(-1).append(CommonConstant.LINE_SEPARATOR);//最后执行
            droolRuleStr.append(CommonConstant.DROOLS_WHEN).append(CommonConstant.LINE_SEPARATOR);
            droolRuleStr.append("$manage:DroolsRuleManager()").append(CommonConstant.LINE_SEPARATOR);
            droolRuleStr.append(CommonConstant.DROOLS_THEN).append(CommonConstant.LINE_SEPARATOR);
            droolRuleStr.append("_result.getMap().put").append("(").append("\"").append(CommonConstant.ORDER_CODE).append("\"").append(",").append("_result.getOrderCode()").append(")").append(";").append(CommonConstant.LINE_SEPARATOR);
            droolRuleStr.append("_result.getMap().put").append("(").append("\"").append(CommonConstant.BUSINESS_CODE).append("\"").append(",").append("\"").append(businessCode)
                    .append("\"").append(")").append(";").append(CommonConstant.LINE_SEPARATOR);
            droolRuleStr.append("_result.getMap().put").append("(").append("\"").append(CommonConstant.PROCESS_CODE).append("\"").append(",").append("\"").append(processCode)
                    .append("\"").append(")").append(";").append(CommonConstant.LINE_SEPARATOR);

            if (CommonRuleEnums.SIMULATION==commonRuleEnums){
                droolRuleStr.append("$manage.recordSimulationExcuteResult(_result.getMap());").append(CommonConstant.LINE_SEPARATOR);
            }
            if (CommonRuleEnums.ORDINARY == commonRuleEnums){
                droolRuleStr.append("$manage.recordExcuteResult(_result.getMap());").append(CommonConstant.LINE_SEPARATOR);
            }

            droolRuleStr.append(CommonConstant.DROOLS_END);

        }

        if (BusinessType.koperasi.getCode().equals(businessCode)) {
            logger.info("俺不处理");//TODO--其他业务先不处理
        }
        return droolRuleStr;
    }
}
