package com.graduation.project.risk.project.biz.manager;

import com.graduation.project.risk.common.core.biz.CommonUtil;
import com.graduation.project.risk.project.biz.dto.RuleFactParams;
import com.graduation.project.risk.project.dal.jpa.dao.pp.PpRiskLoanInfoJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.pp.PpRiskLoanOtherContactsJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RuleBusinessJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRiskLoanInfoDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRiskLoanOtherContactsDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpSystemVariableBO;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleBusinessDO;
import com.graduation.project.risk.project.web.form.DTO.RiskOrderForm;
import com.graduation.project.risk.project.biz.dto.RiskOrderDTO;
import com.graduation.project.risk.project.biz.dto.RuleFactResult;
import com.graduation.project.risk.project.biz.enums.CommonRuleEnums;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import com.graduation.project.risk.project.biz.enums.TriggerLinkEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class DTOManager {

    private Logger logger = LoggerFactory.getLogger(DTOManager.class);

    @Autowired
    private PpRiskLoanInfoJpaDAO ppRiskLoanInfoJpaDAO;

    @Autowired
    private PpRiskLoanOtherContactsJpaDAO contactsJpaDAO;

    @Autowired
    private RuleDroolsManager ruleDroolsManager;

    @Autowired
    private RuleBusinessJpaDAO ruleBusinessJpaDAO;

    /*
    *转换
    * @param RiskOrderForm
     */
    public static RiskOrderDTO setDTO(RiskOrderForm form){
        RiskOrderDTO riskOrderDTO = new RiskOrderDTO();

        riskOrderDTO.setOrderCode(form.getOrderCode());
        riskOrderDTO.setSalaryDay(form.getSalaryDay());
        riskOrderDTO.setDeviceId(form.getDeviceId());
        riskOrderDTO.setApplicationAmount(form.getApplicationAmount());
        riskOrderDTO.setPersonProvince(form.getPersonProvince());
        riskOrderDTO.setPersonCity(form.getPersonCity());
        riskOrderDTO.setPersonDistrict(form.getPersonDistrict());
        riskOrderDTO.setCompanyName(form.getCompanyName());
        riskOrderDTO.setJobTitle(form.getJobTitle());
        riskOrderDTO.setJobLevel(form.getJobLevel());
        riskOrderDTO.setJobType(form.getJobType());
        riskOrderDTO.setMonthlySalary(form.getMonthlySalary());
        riskOrderDTO.setLowestMonthlySalary(form.getLowestMonthlySalary());
        riskOrderDTO.setHighestMonthlySalary(form.getHighestMonthlySalary());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Double applicationTime = form.getApplicationTime();
        if (!CommonUtil.isEmpty(applicationTime)){
            try {
                riskOrderDTO.setApplicationTime(dateFormat.parse(dateFormat.format(applicationTime)));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        Double startOfEmployment = form.getStartOfEmployment();
        if (!CommonUtil.isEmpty(startOfEmployment)){
            try {
                riskOrderDTO.setStartOfEmployment(dateFormat.parse(dateFormat.format(startOfEmployment)));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        riskOrderDTO.setOccupationProvince(form.getOccupationProvince());
        riskOrderDTO.setOccupationCity(form.getOccupationCity());
        riskOrderDTO.setOccupationDistrict(form.getOccupationDistrict());
        riskOrderDTO.setLng(form.getLng());
        riskOrderDTO.setLat(form.getLat());
        riskOrderDTO.setKtpNo(form.getKtpNo());
        riskOrderDTO.setMobile(form.getMobile());
        riskOrderDTO.setMaritalStatus(form.getMaritalStatus().toString());
        riskOrderDTO.setGender(form.getGender());
        riskOrderDTO.setEmail(form.getEmail());
        riskOrderDTO.setEducation(form.getEducation());
        riskOrderDTO.setFullName(form.getFullName());
        riskOrderDTO.setReligion(form.getReligion());
        riskOrderDTO.setContactName1(form.getContactName1());
        riskOrderDTO.setContactPhone1(form.getContactPhone1());
        riskOrderDTO.setContactRelation1(form.getContactRelation1());
        riskOrderDTO.setContactName2(form.getContactName2());
        riskOrderDTO.setContactPhone2(form.getContactPhone2());
        riskOrderDTO.setContactRelation2(form.getContactRelation2());
        riskOrderDTO.setContactName3(form.getContactName3());
        riskOrderDTO.setContactPhone3(form.getContactPhone3());
        riskOrderDTO.setContactRelation3(form.getContactRelation3());
        riskOrderDTO.setContactName4(form.getContactName4());
        riskOrderDTO.setContactPhone4(form.getContactPhone4());
        riskOrderDTO.setContactRelation4(form.getContactRelation4());
        riskOrderDTO.setContactName5(form.getContactName5());
        riskOrderDTO.setContactPhone5(form.getContactPhone5());
        riskOrderDTO.setContactRelation5(form.getContactRelation5());
        riskOrderDTO.setTermOfLoan(form.getTermOfLoan());

        return riskOrderDTO;

    }

    /*
    *保存数据
    * @param RiskOrderForm
     */
    public void saveDO(RiskOrderForm form){
        //保存
        PpRiskLoanInfoDO ppRiskLoanInfoDO = new PpRiskLoanInfoDO();

        ppRiskLoanInfoDO.setOrderCode(form.getOrderCode());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Double applicationTime = form.getApplicationTime();
        if (!CommonUtil.isEmpty(applicationTime)){
            try {
                ppRiskLoanInfoDO.setApplicationTime(dateFormat.parse(dateFormat.format(applicationTime)));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        Double startOfEmployment = form.getStartOfEmployment();
        if (!CommonUtil.isEmpty(startOfEmployment)){
            try {
                ppRiskLoanInfoDO.setStartOfEmployment(dateFormat.parse(dateFormat.format(startOfEmployment)));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        ppRiskLoanInfoDO.setKtpNo(form.getKtpNo());
        ppRiskLoanInfoDO.setMobile(form.getMobile());
        ppRiskLoanInfoDO.setMaritalStatus(form.getMaritalStatus());
        ppRiskLoanInfoDO.setGender(form.getGender());
        ppRiskLoanInfoDO.setSalaryDay(form.getSalaryDay());
        ppRiskLoanInfoDO.setEmail(form.getEmail());
        ppRiskLoanInfoDO.setEducation(form.getEducation());
        ppRiskLoanInfoDO.setDeviceId(form.getDeviceId());
        ppRiskLoanInfoDO.setApplicationAmount(form.getApplicationAmount());
        ppRiskLoanInfoDO.setPersonProvince(form.getPersonProvince());
        ppRiskLoanInfoDO.setPersonCity(form.getPersonCity());
        ppRiskLoanInfoDO.setPersonDistrict(form.getPersonDistrict());
        ppRiskLoanInfoDO.setCompanyName(form.getCompanyName());
        ppRiskLoanInfoDO.setJobTitle(form.getJobTitle());
        ppRiskLoanInfoDO.setJobLevel(form.getJobLevel());
        ppRiskLoanInfoDO.setJobType(form.getJobType());
        ppRiskLoanInfoDO.setMonthlySalary(form.getMonthlySalary());
        ppRiskLoanInfoDO.setLowestMonthlySalary(form.getLowestMonthlySalary());
        ppRiskLoanInfoDO.setHighestMonthlySalary(form.getHighestMonthlySalary());
        ppRiskLoanInfoDO.setOccupationProvince(form.getOccupationProvince());
        ppRiskLoanInfoDO.setOccupationCity(form.getOccupationCity());
        ppRiskLoanInfoDO.setOccupationDistrict(form.getOccupationDistrict());
        ppRiskLoanInfoDO.setFullName(form.getFullName());
        ppRiskLoanInfoDO.setReligion(form.getReligion());
        ppRiskLoanInfoDO.setLng(form.getLng());
        ppRiskLoanInfoDO.setLat(form.getLat());
        ppRiskLoanInfoDO.setTermOfLoan(form.getTermOfLoan());
        ppRiskLoanInfoDO.setBusinessCode(form.getBusinessCode());

        RuleBusinessDO ruleBusinessDO = ruleBusinessJpaDAO.findByBusinessCodeAndStatus(form.getBusinessCode(), CommonStatus.normal);
        if (!CommonUtil.isEmpty(ruleBusinessDO)) {

            ppRiskLoanInfoDO.setBusinessName(ruleBusinessDO.getBusinessName());
        }

        ppRiskLoanInfoJpaDAO.save(ppRiskLoanInfoDO);

        PpRiskLoanOtherContactsDO riskLoanOtherContactsDO = new PpRiskLoanOtherContactsDO();

        riskLoanOtherContactsDO.setOrderCode(form.getOrderCode());
        riskLoanOtherContactsDO.setContactName1(form.getContactName1());
        riskLoanOtherContactsDO.setContactPhone1(form.getContactPhone1());
        riskLoanOtherContactsDO.setContactRelation1(form.getContactRelation1());
        riskLoanOtherContactsDO.setContactName2(form.getContactName2());
        riskLoanOtherContactsDO.setContactPhone2(form.getContactPhone2());
        riskLoanOtherContactsDO.setContactRelation2(form.getContactRelation2());
        riskLoanOtherContactsDO.setContactName3(form.getContactName3());
        riskLoanOtherContactsDO.setContactPhone3(form.getContactPhone3());
        riskLoanOtherContactsDO.setContactRelation3(form.getContactRelation3());
        riskLoanOtherContactsDO.setContactName4(form.getContactName4());
        riskLoanOtherContactsDO.setContactPhone4(form.getContactPhone4());
        riskLoanOtherContactsDO.setContactRelation4(form.getContactRelation4());
        riskLoanOtherContactsDO.setContactName5(form.getContactName5());
        riskLoanOtherContactsDO.setContactPhone5(form.getContactPhone5());
        riskLoanOtherContactsDO.setContactRelation5(form.getContactRelation5());

        contactsJpaDAO.save(riskLoanOtherContactsDO);

    }

    public void dto(RiskOrderForm form){
        RuleFactParams ruleFactParams = new RuleFactParams();
        RiskOrderDTO riskOrderDTO = setDTO(form);
        this.saveDO(form);
        PpSystemVariableBO ppSystemVariableBO = new PpSystemVariableBO(riskOrderDTO);
        ruleFactParams.addFact(ppSystemVariableBO);//暂时只传入一个fact对象
        RuleFactResult ruleFactResult = new RuleFactResult();
        ruleFactResult.setOrderCode(riskOrderDTO.getOrderCode());
        ruleFactParams.setGlobal("_result",ruleFactResult);
        String processCode = form.getProcessCode();
        String businessCode = form.getBusinessCode();
        TriggerLinkEnums triggerLink = form.getTriggerLink();
        ruleDroolsManager.excute(CommonRuleEnums.ORDINARY,businessCode,ruleFactParams,processCode,triggerLink);
    }

}
