package com.graduation.project.risk.project.biz.manager;

import com.github.pagehelper.PageHelper;
import com.graduation.project.risk.common.core.biz.BizCoreException;
import com.graduation.project.risk.common.core.biz.CommonUtil;
import com.graduation.project.risk.common.core.biz.ErrorCode;
import com.graduation.project.risk.common.core.biz.StringUtil;
import com.graduation.project.risk.common.core.dal.mongdb.query.PageUtil;
import com.graduation.project.risk.project.biz.enums.*;
import com.graduation.project.risk.project.dal.jpa.dao.pp.PpRuleSystemVariableJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RuleBusinessJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RuleVariableJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRuleSystemVariableDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleBusinessDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleVariableDO;
import com.graduation.project.risk.project.dal.mybaits.dao.RuleVariableMapper;
import com.graduation.project.risk.project.web.form.rule.RuleVariableForm;
import com.graduation.project.risk.project.web.form.rule.VariableAddForm;
import com.graduation.project.risk.project.web.form.rule.VariableUpdateForm;
import com.graduation.project.risk.project.web.vo.KeyValueVO;
import com.graduation.project.risk.project.web.vo.rule.RuleVariableVO;
import com.graduation.project.risk.common.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleVariableManager {

    private Logger logger = LoggerFactory.getLogger(RuleVariableManager.class);

    @Autowired
    private RuleVariableMapper ruleVariableMapper;

    @Autowired
    private RuleBusinessJpaDAO ruleBusinessJpaDAO;

    @Autowired
    private RuleVariableJpaDAO ruleVariableJpaDAO;

    @Autowired
    private PpRuleSystemVariableJpaDAO ppRuleSystemVariableJpaDAO;

    @Autowired
    private LogManager logManager;

    /**
     * 变量管理列表页查询
     *
     * @param ruleVariableForm
     * @return
     */
    public Page<RuleVariableVO> ruleVariableSearch(RuleVariableForm ruleVariableForm){
       // List<RuleVariableVO> VariableVOS = new HashSet<>();
        PageHelper.startPage(ruleVariableForm.getPageNumber(), ruleVariableForm.getPageSize());
        String variableName = ruleVariableForm.getVariableName();
        String businessCode = ruleVariableForm.getBusinessCode();
        String variableType = ruleVariableForm.getVariableType();
        String dataType = ruleVariableForm.getDataType();
        String variableDescribe =ruleVariableForm.getVariableDescribe();
        if(StringUtil.isEmpty(variableName) && StringUtil.isEmpty(businessCode) && StringUtil.isEmpty(variableType)
                && StringUtil.isEmpty(dataType) && StringUtil.isEmpty(variableDescribe)){
            throw new BizCoreException(ErrorCode.PARAM_MISS);
        }

        List<RuleVariableVO> VariableVOS = ruleVariableMapper.variableSearch(variableName,businessCode,variableType,dataType,variableDescribe);
        Page<RuleVariableVO> page = PageUtil.toPage(VariableVOS);

        return page;
    }

    /**
     * variable update
     * @param variableUpdateForm
     * @return
     */
    public boolean variableUpdate(VariableUpdateForm variableUpdateForm) {
        Long variableId = variableUpdateForm.getVariableId();
        RuleVariableDO ruleVariableDO = ruleVariableJpaDAO.getOneById(variableId);
        if (CommonUtil.isEmpty(ruleVariableDO)){
            throw new BizCoreException(ErrorCode.BUSINESS_CODE_NOT_EXISTED);
        }

        String businessCode = variableUpdateForm.getBusinessCode();

        //校验业务code是否正确
        RuleBusinessDO ruleBusinessDO = ruleBusinessJpaDAO.findByBusinessCodeAndStatus(businessCode, CommonStatus.normal);
        if (CommonUtil.isEmpty(ruleBusinessDO)){
            throw new BizCoreException(ErrorCode.BUSINESS_CODE_NOT_EXISTED);
        }
        ruleVariableDO.setBusinessCode(businessCode);
        ruleVariableDO.setBusinessName(ruleBusinessDO.getBusinessName());

        if (!ruleVariableDO.getVariableName().equals(variableUpdateForm.getVariableName())){
            RuleVariableDO ruleVariableNameDO = ruleVariableJpaDAO.findByBusinessCodeAndVariableName(businessCode,variableUpdateForm.getVariableName());
            if (!CommonUtil.isEmpty(ruleVariableNameDO)){
                throw new BizCoreException(ErrorCode.VARIABLE_NAMW_IS_EXISTED);
            }
        }

        ruleVariableDO.setVariableName(variableUpdateForm.getVariableName());

        ruleVariableDO.setVariableDescribe(variableUpdateForm.getVariableDescribe());
        ruleVariableDO.setVariableType(VariableType.convertFrom(variableUpdateForm.getVariableType()));
        ruleVariableDO.setDataType(variableUpdateForm.getDataType());

        //默认值
        if (variableUpdateForm.getIfCanNull().equals(IfCanNull.no.getCode())){
            if (CommonUtil.isEmpty(variableUpdateForm.getDefaultData()))
                throw new BizCoreException(ErrorCode.DEFAULT_IS_NULL);
        }
        ruleVariableDO.setIfCanNull(IfCanNull.convertFrom(variableUpdateForm.getIfCanNull()));
        ruleVariableDO.setDefaultData(variableUpdateForm.getDefaultData());

        //对应系统变量校验
        if (businessCode.equals(BusinessType.pp.getCode())){//TODO---pp业务逻辑，目前只处理pp业务
            PpRuleSystemVariableDO ppRuleSystemVariableDO = ppRuleSystemVariableJpaDAO.findByVariableName(variableUpdateForm.getSystemVariable());
            if (CommonUtil.isEmpty(ppRuleSystemVariableDO)){
                throw new BizCoreException(ErrorCode.SYSTEM_VARIABLE_NOT_EXISTED);
            }
            ruleVariableDO.setSystemVariableId(ppRuleSystemVariableDO.getId());
        }else{
            ruleVariableDO.setSystemVariableId(0L);
        }

        if (!CommonUtil.isEmpty(variableUpdateForm.getScriptType())){//TODO---脚本类型暂时先这么处理
            ruleVariableDO.setScriptType(ScriptType.convertFrom(variableUpdateForm.getScriptType()));
        }
        if (!CommonUtil.isEmpty(variableUpdateForm.getSchematic())){
            ruleVariableDO.setSchematic(variableUpdateForm.getSchematic());
        }else {
            ruleVariableDO.setSchematic("");
        }
        if (!CommonUtil.isEmpty(variableUpdateForm.getTitle())){
            ruleVariableDO.setTitle(variableUpdateForm.getTitle());
        }else {
            ruleVariableDO.setTitle("");
        }
        if (!CommonUtil.isEmpty(variableUpdateForm.getDescription())){
            ruleVariableDO.setDescription(variableUpdateForm.getDescription());
        }else {
            ruleVariableDO.setDescription("");
        }
        //TODO---当变量类型选择DERIVATION时显示且必填，否则隐藏非必填
        if(!CommonUtil.isEmpty(variableUpdateForm.getScriptContent())){
            ruleVariableDO.setScriptContent(variableUpdateForm.getScriptContent());
        }else{
            ruleVariableDO.setScriptContent("");
        }

        ruleVariableJpaDAO.save(ruleVariableDO);

        RuleVariableDO ruleVariable = ruleVariableJpaDAO.findByBusinessCodeAndVariableName(businessCode,variableUpdateForm.getVariableName());
        Long ruleVariableId = ruleVariable.getId();
        String operatorType = "update_variable";
        logManager.variableLog(ruleVariableId,operatorType);

        return true;
    }

    public RuleVariableVO getRuleVariableInfo(Long variableId){
        RuleVariableVO variableVO = ruleVariableMapper.getRuleVariableInfo(variableId);

        return variableVO;
    }


    /**
     * 变量新增
     * @param variableAddForm
     * @return
     */
    public boolean variableAdd(VariableAddForm variableAddForm) {
        //校验业务code是否正确
        String businessCode = variableAddForm.getBusinessCode();
        RuleBusinessDO ruleBusinessDO = ruleBusinessJpaDAO.findByBusinessCodeAndStatus(businessCode, CommonStatus.normal);
        if (CommonUtil.isEmpty(ruleBusinessDO)) {
            throw new BizCoreException(ErrorCode.BUSINESS_CODE_NOT_EXISTED);
        }

        //变量名称不能重复
        RuleVariableDO ruleVariableName = ruleVariableJpaDAO.findByBusinessCodeAndVariableName(businessCode, variableAddForm.getVariableName());
        if (!CommonUtil.isEmpty(ruleVariableName)) {
            throw new BizCoreException(ErrorCode.VARIABLE_NAMW_IS_EXISTED);
        }

        //默认值
        if (variableAddForm.getIfCanNull().equals(IfCanNull.no)) {
            if (CommonUtil.isEmpty(variableAddForm.getDefaultData()))
                throw new BizCoreException(ErrorCode.DEFAULT_IS_NULL);
        }

        RuleVariableDO ruleVariableDO = new RuleVariableDO(variableAddForm);
        ruleVariableDO.setBusinessName(ruleBusinessDO.getBusinessName());

        //对应系统变量校验
        String systemVariable = variableAddForm.getSystemVariable();
        if (businessCode.equals(BusinessType.pp.getCode())) {//TODO---pp业务逻辑，目前只处理pp业务
            PpRuleSystemVariableDO ppRuleSystemVariableDO = ppRuleSystemVariableJpaDAO.findByVariableName(systemVariable);
            if (CommonUtil.isEmpty(ppRuleSystemVariableDO)) {
                throw new BizCoreException(ErrorCode.SYSTEM_VARIABLE_NOT_EXISTED);
            }
            ruleVariableDO.setSystemVariableId(ppRuleSystemVariableDO.getId());

        }

        ruleVariableJpaDAO.save(ruleVariableDO);

        RuleVariableDO ruleVariable = ruleVariableJpaDAO.findByBusinessCodeAndVariableName(businessCode, variableAddForm.getVariableName());
        Long ruleVariableId = ruleVariable.getId();
        String operatorType = "add_variable";
        logManager.variableLog(ruleVariableId,operatorType);

        return true;
    }

    /**
     * 获取业务数据
     *
     * @return key=bizCode, value=bizName
     */
    public List<KeyValueVO> getBizList() {

        List<KeyValueVO> list = new ArrayList<>();

        List<RuleBusinessDO> bizList = ruleBusinessJpaDAO.findByStatusAndBusinessName(CommonStatus.normal, "pp");

        if (bizList == null || bizList.size() == 0) {
            return list;
        }

        for (RuleBusinessDO biz : bizList) {
            list.add(new KeyValueVO(biz.getBusinessCode(), biz.getBusinessName()));
        }

        return list;
    }

    public RuleVariableDO getOne(Long varId) {
        return ruleVariableJpaDAO.findOne(varId);
    }

}
