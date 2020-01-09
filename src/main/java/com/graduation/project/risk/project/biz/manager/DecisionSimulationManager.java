package com.graduation.project.risk.project.biz.manager;

import com.github.pagehelper.PageHelper;
import com.graduation.project.risk.common.core.biz.BizCoreException;
import com.graduation.project.risk.common.core.biz.StringUtil;
import com.graduation.project.risk.integration.RedisService;
import com.graduation.project.risk.project.biz.dto.RuleFactParams;
import com.graduation.project.risk.project.biz.util.DroolsUtil;
import com.graduation.project.risk.project.web.form.decision.RuleSimulationForm;
import com.graduation.project.risk.project.web.form.decision.SimulationSearchForOneForm;
import com.graduation.project.risk.project.web.form.decision.SimulationSearchForm;
import com.graduation.project.risk.common.core.biz.CommonUtil;
import com.graduation.project.risk.common.core.biz.ErrorCode;
import com.graduation.project.risk.common.core.dal.mongdb.query.PageUtil;
import com.graduation.project.risk.common.model.Page;
import com.graduation.project.risk.project.biz.constants.CommonConstant;
import com.graduation.project.risk.project.biz.dto.RiskOrderDTO;
import com.graduation.project.risk.project.biz.dto.RuleFactResult;
import com.graduation.project.risk.project.biz.enums.BusinessType;
import com.graduation.project.risk.project.biz.enums.CommonRuleEnums;
import com.graduation.project.risk.project.biz.enums.TriggerLinkEnums;
import com.graduation.project.risk.project.dal.jpa.dao.pp.PpRiskLoanInfoJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RuleProcessJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRiskLoanInfoDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpSystemVariableBO;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleProcessDO;
import com.graduation.project.risk.project.dal.mybaits.dao.DecisionSimulationMapper;
import com.graduation.project.risk.project.dal.mybaits.dao.RiskOrderMapper;
import com.graduation.project.risk.project.web.form.decision.OrderSearchForm;
import com.graduation.project.risk.project.web.vo.decision.DecisionSimulationVO;
import com.graduation.project.risk.project.web.vo.decision.RiskOrderVO;
import com.graduation.project.risk.project.web.vo.decision.RuleSimulationListVO;
import com.graduation.project.risk.project.web.vo.decision.RuleSimulationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DecisionSimulationManager {

    private Logger logger = LoggerFactory.getLogger(DecisionSimulationManager.class);

    @Autowired
    private RuleProcessJpaDAO ruleProcessJpaDAO;

    @Autowired
    private DecisionSimulationMapper decisionSimulationMapper;

    @Autowired
    private RiskOrderMapper riskOrderMapper;

    @Autowired
    private RuleDroolsManager ruleDroolsManager;

    @Autowired
    private RedisService redisService;

    @Autowired
    private PpRiskLoanInfoJpaDAO ppRiskLoanInfoJpaDAO;

    /*
     *决策模拟列表页查询
     * @param SimulationSearchForm
     * @return
     */
    public Page<DecisionSimulationVO> processSearch(SimulationSearchForm simulationSearchForm) {
        PageHelper.startPage(simulationSearchForm.getPageNumber(), simulationSearchForm.getPageSize());
        String businessCode = simulationSearchForm.getBusinessCode();
        String processCode = simulationSearchForm.getProcessCode();
        if (StringUtil.isEmpty(businessCode) && StringUtil.isEmpty(processCode)) {
            throw new BizCoreException(ErrorCode.PARAM_MISS);
        }

        List<DecisionSimulationVO> simulationVOS = decisionSimulationMapper.processSearch(businessCode, processCode, CommonConstant.NORMAL_FLAG);
        Page<DecisionSimulationVO> page = PageUtil.toPage(simulationVOS);

        return page;

    }

    /*
     *点击规则模拟进入选择页面
     *@param SimulationSearchForm
     * @return
     */
    public DecisionSimulationVO editSearch(SimulationSearchForOneForm simulationSearchForOneForm) {
        RuleProcessDO edit = ruleProcessJpaDAO.findByIdAndFlag(simulationSearchForOneForm.getProcessId(), CommonConstant.NORMAL_FLAG);
        if (null == edit) {
            throw new BizCoreException(ErrorCode.NO_RECORD);
        }
        DecisionSimulationVO decisionSimulationVO = new DecisionSimulationVO();
        decisionSimulationVO.setProcessId(edit.getId());
        decisionSimulationVO.setBusinessName(edit.getBusinessName());
        decisionSimulationVO.setProcessName(edit.getProcessName());
        decisionSimulationVO.setProcessDescribe(edit.getProcessDescribe());
        decisionSimulationVO.setUpdateTime(edit.getUpdateTime());
        decisionSimulationVO.setBusinessCode(edit.getBusinessCode());
        decisionSimulationVO.setProcessCode(edit.getProcessCode());
        decisionSimulationVO.setTriggerLinkEnums(edit.getTriggerLink().toString());

        return decisionSimulationVO;
    }

    /*
     *订单查询
     * @param OrderSearchForm
     * @return
     */
    public Page<RiskOrderVO> riskOrderSearch(OrderSearchForm orderSearchForm) {
        PageHelper.startPage(orderSearchForm.getPageNumber(),orderSearchForm.getPageSize());
        List<String> dateList = new ArrayList<>();
        String startDate = orderSearchForm.getStartDate();
        String endDate = orderSearchForm.getEndDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtil.isNotBlank(startDate) && StringUtil.isNotBlank(endDate)) {
            try {
                Long startDate_ = Long.valueOf(dateFormat.parse(startDate).getTime());
                Long endDate_ = Long.valueOf(dateFormat.parse(endDate).getTime());
                if (startDate_ < endDate_) {
                    dateList = findDates(startDate,endDate);
                }
                if (dateList.size() > 7) {
                    throw new BizCoreException(ErrorCode.TIME_IS_TOO_LONG);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String dataType = orderSearchForm.getDataType();
        List<RiskOrderVO> riskOrderVO = riskOrderMapper.riskOrderSearch(startDate, endDate, dataType);

        Page<RiskOrderVO> page = PageUtil.toPage(riskOrderVO);

        return page;
    }

    private List<String> findDates(String startDate, String endDate) throws ParseException {
        List<String> allDate = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dBegin = sdf.parse(startDate);
        Date dEnd = sdf.parse(endDate);
        allDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的Date设置此Calendar的时间
        calBegin.setTime(dBegin);
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            allDate.add(sdf.format(calBegin.getTime()));
        }
        return allDate;
    }


    /**
     * 规则模拟
     *
     * @param ruleSimulationForm
     * @return
     */
    public synchronized RuleSimulationVO ruleSimulation(RuleSimulationForm ruleSimulationForm) {
        String businessCode = ruleSimulationForm.getBusinessCode();
        String processCode = ruleSimulationForm.getProcessCode();
        String triggerLinkEnums = ruleSimulationForm.getTriggerLinkEnums();
        List<String> orderCodeList = ruleSimulationForm.getOrderCodeList();

        if (BusinessType.pp.getCode().equals(businessCode)) {//TODO--处理pp业务
            if (CommonUtil.isEmpty(orderCodeList)) {
                logger.error("orderCodeList is null.orderCodeList:" + orderCodeList);
                throw new BizCoreException(ErrorCode.MISSING_PARAMETER);
            }
            List<RiskOrderDTO> riskOrderDTOS = getOrderInfo(orderCodeList);
            if (!CommonUtil.isEmpty(riskOrderDTOS)) {
                for (RiskOrderDTO riskOrderDTO : riskOrderDTOS) {
                    RuleFactParams ruleFactParams = new RuleFactParams();
                    PpSystemVariableBO ppSystemVariableBO = new PpSystemVariableBO(riskOrderDTO);
                    ruleFactParams.addFact(ppSystemVariableBO);
                    RuleFactResult ruleFactResult = new RuleFactResult();
                    ruleFactResult.setOrderCode(riskOrderDTO.getOrderCode());
                    ruleFactParams.setGlobal("_result", ruleFactResult);
                    TriggerLinkEnums triggerLink = TriggerLinkEnums.convertFrom(triggerLinkEnums);
                    ruleDroolsManager.excute(CommonRuleEnums.SIMULATION, businessCode, ruleFactParams, processCode, triggerLink);
                }
            }

            //清空规则缓存
            DroolsUtil.removeRuleMap(processCode + CommonRuleEnums.SIMULATION.getCode());

            //统计各项数据
            RuleSimulationVO ruleSimulationVO = new RuleSimulationVO();
            int testUserNumber = 0;//测试用户数
            int failNumber = 0;//授信失败数
            int successNumber = 0;//授信成功数
            int maxCreditAmount = 0;//最大授信额度
            BigDecimal totalAmount = BigDecimal.ZERO;//所有的授信额度总和

            List<RuleSimulationListVO> ruleSimulationListVOList = new ArrayList<>();
            List<String> xData = new ArrayList<>();
            Set set = new HashSet();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            for (RiskOrderDTO riskOrderDTO : riskOrderDTOS) {
                RuleSimulationListVO ruleSimulationListVO = new RuleSimulationListVO();
                String orderCode = riskOrderDTO.getOrderCode();
                PpRiskLoanInfoDO ppRiskLoanInfoDO = ppRiskLoanInfoJpaDAO.findByOrderCode(orderCode);
                String result = redisService.get(orderCode + CommonRuleEnums.SIMULATION.getCode());

                testUserNumber += 1;
                //授信失败未包括复议
                if (CommonRuleEnums.REJECT.getCode().equals(result)) {
                    failNumber += 1;
                }
                if (CommonRuleEnums.PASS.getCode().equals(result)) {
                    successNumber += 1;
                    totalAmount = totalAmount.add(new BigDecimal(ppRiskLoanInfoDO.getApplicationAmount()));

                    String applicationAmount = ppRiskLoanInfoDO.getApplicationAmount();
                    if (maxCreditAmount < Integer.valueOf(applicationAmount)) {
                        maxCreditAmount = Integer.valueOf(applicationAmount);
                    }
                }
                ruleSimulationListVO.setApplicationAmount(ppRiskLoanInfoDO.getApplicationAmount());
                String data = df(ppRiskLoanInfoDO.getCreateTime(),formatter);
                ruleSimulationListVO.setCreateTime(data);
                set.add(data);
                ruleSimulationListVO.setBusinessName(ppRiskLoanInfoDO.getBusinessName());
                ruleSimulationListVO.setFullName(ppRiskLoanInfoDO.getFullName());
                ruleSimulationListVO.setMobile(ppRiskLoanInfoDO.getMobile());
                ruleSimulationListVO.setOrderCode(ppRiskLoanInfoDO.getOrderCode());
                ruleSimulationListVO.setSimulationResult(result);
                ruleSimulationListVO.setTermLoan(ppRiskLoanInfoDO.getTermOfLoan());
                ruleSimulationListVOList.add(ruleSimulationListVO);

                redisService.delete(orderCode + CommonRuleEnums.SIMULATION.getCode());
            }
            Iterator it = set.iterator();
            while (it.hasNext()) {
                xData.add((String) it.next());
            }
            ruleSimulationVO.setXData(xData);
            ruleSimulationVO.setTestUserNumber(testUserNumber);
            ruleSimulationVO.setFailNumber(failNumber);
            ruleSimulationVO.setSuccessNumber(successNumber);
            ruleSimulationVO.setSuccessRate((new BigDecimal((float) successNumber / testUserNumber).setScale(2, BigDecimal.ROUND_DOWN)) + "");
            ruleSimulationVO.setMaxCreditAmount(maxCreditAmount + "");
            ruleSimulationVO.setAverageCreditAmount(successNumber == 0 ? "0" : (totalAmount.divide(new BigDecimal(successNumber), 2, BigDecimal.ROUND_DOWN).doubleValue()) + "");
            ruleSimulationVO.setRuleSimulationListVOList(ruleSimulationListVOList);


            return ruleSimulationVO;
        }

        return null;


    }

    private String df(Date date, SimpleDateFormat formatter) {

        String StringDate = formatter.format(date);

        return StringDate;
    }

    /**
     * 根据订单号（借款编号）查询出借款信息
     *
     * @param orderCodeList
     * @return
     */
    public List<RiskOrderDTO> getOrderInfo(List<String> orderCodeList) {

        List<RiskOrderDTO> riskOrderDTO = riskOrderMapper.getOrderInfo(orderCodeList);

        return riskOrderDTO;
    }


}
