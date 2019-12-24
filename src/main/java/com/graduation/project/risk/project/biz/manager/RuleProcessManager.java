package com.graduation.project.risk.project.biz.manager;

import com.github.pagehelper.PageHelper;
import com.graduation.project.risk.common.core.biz.BizCoreException;
import com.graduation.project.risk.common.core.biz.StringUtil;
import com.graduation.project.risk.project.biz.util.DroolsUtil;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RulePackageJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleBusinessDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RulePackageDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleProcessDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleProcessPackageRelDO;
import com.graduation.project.risk.project.dal.mybaits.dao.RuleProcessMapper;
import com.graduation.project.risk.project.web.form.rule.*;
import com.graduation.project.risk.project.web.vo.rule.ProcessEditVO;
import com.graduation.project.risk.project.web.vo.rule.RuleProcessPackageSearchVO;
import com.graduation.project.risk.project.web.vo.rule.RuleProcessPackageVO;
import com.graduation.project.risk.project.web.vo.rule.RuleProcessVO;
import com.graduation.project.risk.common.core.biz.CommonUtil;
import com.graduation.project.risk.common.core.biz.ErrorCode;
import com.graduation.project.risk.common.core.dal.mongdb.query.PageUtil;
import com.graduation.project.risk.common.model.Page;
import com.graduation.project.risk.project.biz.constants.CommonConstant;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import com.graduation.project.risk.project.biz.enums.PublishStatus;
import com.graduation.project.risk.project.biz.enums.TriggerLinkEnums;

import com.graduation.project.risk.project.dal.jpa.dao.rule.RuleBusinessJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RuleProcessJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RuleProcessPackageRelJpaDAO;
import com.graduation.project.risk.project.web.form.rule.ProcessAddForm;
import com.graduation.project.risk.project.web.form.rule.ProcessManageForm;
import com.graduation.project.risk.project.web.form.rule.ProcessManagePackageForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class RuleProcessManager {

    private Logger logger = LoggerFactory.getLogger(RuleProcessManager.class);

    @Autowired
    private RuleBusinessJpaDAO ruleBusinessJpaDAO;
    @Autowired
    private RuleProcessJpaDAO ruleProcessJpaDAO;
    @Autowired
    private RuleProcessMapper ruleProcessMapper;
    @Autowired
    private RuleProcessPackageRelJpaDAO ruleProcessPackageRelJpaDAO;
    @Autowired
    private RulePackageJpaDAO rulePackageJpaDAO;



    public boolean ruleProcessAdd(ProcessAddForm processAddForm) {
        //校验业务code是否正确
        String businessCode = processAddForm.getBusinessCode();
        RuleBusinessDO ruleBusinessDO = ruleBusinessJpaDAO.findByBusinessCodeAndStatus(businessCode, CommonStatus.normal);
        if (CommonUtil.isEmpty(ruleBusinessDO)) {
            throw new BizCoreException(ErrorCode.BUSINESS_CODE_NOT_EXISTED);
        }

        RuleProcessDO ruleProcessDOResult = ruleProcessJpaDAO.findByProcessCodeAndFlag(processAddForm.getProcessCode(), CommonConstant.NORMAL_FLAG);
        if (!CommonUtil.isEmpty(ruleProcessDOResult)){
            throw new BizCoreException(ErrorCode.PROCESS_IS_EXISTED);
        }

        RuleProcessDO ruleProcessDO = new RuleProcessDO();
        ruleProcessDO.setBusinessCode(businessCode);
        ruleProcessDO.setFlag(CommonConstant.NORMAL_FLAG);
        ruleProcessDO.setProcessCode(processAddForm.getProcessCode());
        ruleProcessDO.setProcessDescribe(processAddForm.getDescribe());
        ruleProcessDO.setProcessName(processAddForm.getProcessName());
        ruleProcessDO.setPublishStatus(PublishStatus.unpublished);
        ruleProcessDO.setTriggerLink(processAddForm.getTriggerLink());
        ruleProcessDO.setBusinessName(ruleBusinessDO.getBusinessName());
        ruleProcessJpaDAO.save(ruleProcessDO);

        return true;
    }
    /*
    *规则流程列表页查询
    * @param RuleProcesForm
    * @return
     */
    public Page<RuleProcessVO> ruleProcessSearch(RuleProcessForm ruleProcessForm){
        PageHelper.startPage(ruleProcessForm.getPageNumber(),ruleProcessForm.getPageSize());
        String processName = ruleProcessForm.getProcessName();
        String businessCode = ruleProcessForm.getBusinessCode();
        if (StringUtil.isEmpty(processName) && StringUtil.isEmpty(businessCode)){
            throw new BizCoreException(ErrorCode.PARAM_MISS);
        }

        List<RuleProcessVO> processVOS = ruleProcessMapper.processSearch(processName,businessCode,CommonConstant.NORMAL_FLAG);
        Page<RuleProcessVO> page = PageUtil.toPage(processVOS);

        return page;
    }
    /*
    *删除
    * @param ProcessDeleteForm
    * @return
     */
    public boolean ruleProcessDelete(ProcessDeleteForm processDeleteForm){
        Long processId = processDeleteForm.getProcessId();
        RuleProcessDO ruleProcessDO = ruleProcessJpaDAO.findByIdAndFlag(processId,CommonConstant.NORMAL_FLAG);
        if (null == ruleProcessDO){
            throw new BizCoreException(ErrorCode.PROCESS_INFO_NOT_EXISTED);
        }
        if (PublishStatus.published.equals(ruleProcessDO.getPublishStatus())){
            throw new BizCoreException(ErrorCode.PROCESS_IS_PUBLISH);
        }

        ruleProcessDO.setFlag(CommonConstant.DELETE_FLAG);
        ruleProcessJpaDAO.save(ruleProcessDO);

        List<RuleProcessPackageRelDO> delete = ruleProcessPackageRelJpaDAO.findByRuleProcessIdAndFlag(processDeleteForm.getProcessId(),CommonConstant.NORMAL_FLAG);
        for (RuleProcessPackageRelDO list : delete){
            list.setFlag(CommonConstant.DELETE_FLAG);
            ruleProcessPackageRelJpaDAO.save(list);
        }

        return true;
    }
    /*
    *发布
    * @param ProcessReleaseForm
    *
     */
    public void release(ProcessReleaseForm processReleaseForm){
        RuleProcessDO processDO = ruleProcessJpaDAO.findByIdAndFlag(processReleaseForm.getProcessId(),CommonConstant.NORMAL_FLAG);
        if (null == processDO){
            throw new BizCoreException(ErrorCode.PROCESS_ID_ERROR);
        }
        TriggerLinkEnums triggerLink = processDO.getTriggerLink();
        String businessName = processDO.getBusinessName();
        RuleProcessDO process = ruleProcessJpaDAO.findByBusinessNameAndTriggerLinkAndPublishStatus(businessName,triggerLink,PublishStatus.published);
        if (null != process){
            throw new BizCoreException(ErrorCode.PROCESS_IS_PUBLISH);
        }

        List<RuleProcessPackageRelDO> list = ruleProcessPackageRelJpaDAO.findByRuleProcessIdAndFlag(processReleaseForm.getProcessId(),CommonConstant.NORMAL_FLAG);
        for (RuleProcessPackageRelDO get : list){
            String packageCode = get.getRulePackageCode();
            RulePackageDO packageDO = rulePackageJpaDAO.findByPackageCodeAndFlagAndPublishStatus(packageCode,CommonConstant.NORMAL_FLAG,PublishStatus.published);
            if (null != packageDO){
                continue;
            }
            else {
                List<RulePackageDO> version = rulePackageJpaDAO.findByPackageCodeAndFlag(packageCode,CommonConstant.NORMAL_FLAG);
                RulePackageDO release = version.get(version.size() - 1);
                for (RulePackageDO rulePackageDO : version){
                    if (rulePackageDO.getId() <= release.getId()){
                        continue;
                    }
                    release = rulePackageDO;
                }
                RulePackageDO status = rulePackageJpaDAO.findById(release.getId());
                status.setPublishStatus(PublishStatus.published);
                rulePackageJpaDAO.save(status);
            }
        }

        processDO.setPublishStatus(PublishStatus.published);
        ruleProcessJpaDAO.save(processDO);

        DroolsUtil.removeRuleMap(processReleaseForm.getProcessId().toString());

    }
    /*
     *下线
     * @param ProcessOfflineForm
     *
     */
    public void offline(ProcessOfflineForm processOfflineForm){
        RuleProcessDO processDO = ruleProcessJpaDAO.findByIdAndFlag(processOfflineForm.getProcessId(),CommonConstant.NORMAL_FLAG);
        processDO.setPublishStatus(PublishStatus.unpublished);
        ruleProcessJpaDAO.save(processDO);

        List<RuleProcessPackageRelDO> release = ruleProcessPackageRelJpaDAO.findByRuleProcessIdAndFlag(processOfflineForm.getProcessId(),CommonConstant.NORMAL_FLAG);
        for (RuleProcessPackageRelDO list : release){
            list.setFlag(CommonConstant.DELETE_FLAG);
            ruleProcessPackageRelJpaDAO.save(list);
        }
    }
    /*
    *获取规程流程具体信息
    * @param ProcessGetInfoForm
    * @return ProcessEditVO
     */
    public ProcessEditVO getProcessInfo(ProcessGetInfoForm processGetInfoForm){
        RuleProcessDO edit = ruleProcessJpaDAO.findByIdAndFlag(processGetInfoForm.getProcessId(),CommonConstant.NORMAL_FLAG);
        if (null == edit){
            throw new BizCoreException(ErrorCode.NO_RECORD);
        }

        ProcessEditVO processEditVO = new ProcessEditVO();
        processEditVO.setBusinessName(edit.getBusinessName());
        processEditVO.setProcessName(edit.getProcessName());
        processEditVO.setProcessCode(edit.getProcessCode());
        processEditVO.setTriggerLink(edit.getTriggerLink());
        processEditVO.setProcessDescribe(edit.getProcessDescribe());
        processEditVO.setProcessId(processGetInfoForm.getProcessId());

        return processEditVO;
    }
    /*
    *编辑
    * @param ProcessEditForm
     */
    public void edit(ProcessEditForm processEditForm){
        RuleProcessDO exist = ruleProcessJpaDAO.findByProcessCodeAndFlag(processEditForm.getProcessCode(),CommonConstant.NORMAL_FLAG);
        if (null != exist && !(exist.getId() == processEditForm.getProcessId())){
            throw new BizCoreException(ErrorCode.PACKAGE_CODE_IS_EXISTED);
        }
        RuleProcessDO edit = ruleProcessJpaDAO.findByIdAndFlag(processEditForm.getProcessId(),CommonConstant.NORMAL_FLAG);
        if (null == edit){
            throw new BizCoreException(ErrorCode.PROCESS_ID_ERROR);
        }

        edit.setBusinessName(processEditForm.getBusinessName());
        edit.setBusinessCode(processEditForm.getBusinessCode());
        edit.setProcessName(processEditForm.getProcessName());
        edit.setProcessCode(processEditForm.getProcessCode());
        edit.setTriggerLink(TriggerLinkEnums.convertFrom(processEditForm.getTriggerLink()));
        edit.setProcessDescribe(processEditForm.getProcessDescribe());

        ruleProcessJpaDAO.save(edit);

    }
    /*
    *找规则包
    * @param packageCode businessCode
    * @return
     */
    public RuleProcessPackageSearchVO packageSearch(ProcessPackageSearchForm processPackageSearchForm){
        String businessCode = processPackageSearchForm.getBusinessCode();
        String packageCode = processPackageSearchForm.getPackageCode();
        RulePackageDO packageDO = rulePackageJpaDAO.findByBusinessCodeAndAndPackageCodeAndPublishStatusAndFlag(businessCode,packageCode,PublishStatus.published,CommonConstant.NORMAL_FLAG);
        if(null == packageDO){
            throw new BizCoreException(ErrorCode.PACKAGE_INFO_NOT_EXISTED);
        }
        RuleProcessPackageSearchVO packageVO = new RuleProcessPackageSearchVO();
        packageVO.setPackageId(packageDO.getId());
        packageVO.setPackageCode(packageDO.getPackageCode());
        packageVO.setPackageName(packageDO.getPackageName());

        return packageVO;
    }

    /**
     * 规则流程管理（规则包的排序）
     * @param processManageForm
     * @return
     */
    public boolean ruleProcessManage(ProcessManageForm processManageForm) {
        Long processId = processManageForm.getProcessId();
        RuleProcessDO ruleProcessDO = ruleProcessJpaDAO.findByIdAndFlag(processId,CommonConstant.NORMAL_FLAG);
        if (CommonUtil.isEmpty(ruleProcessDO)){
            throw new BizCoreException(ErrorCode.PROCESS_IS_NOT_EXISTED);
        }

        List<ProcessManagePackageForm> processManagePackageForms = processManageForm.getProcessManagePackageFormList();
        if (!CommonUtil.isEmpty(processManagePackageForms)){
            //查找原规则流程对应的所有规则包，删除
            List<RuleProcessPackageRelDO> ruleProcessPackageRelDOs = ruleProcessPackageRelJpaDAO.findByRuleProcessIdAndFlag(processId,CommonConstant.NORMAL_FLAG);
            if (!CommonUtil.isEmpty(ruleProcessPackageRelDOs)){
                ruleProcessPackageRelJpaDAO.updateFlag(processId,CommonConstant.DELETE_FLAG);
            }

            List<RuleProcessPackageRelDO> ruleProcessPackageRelDOS = new ArrayList<>();
            //新增新的规则包
            for (ProcessManagePackageForm processManagePackageForm : processManagePackageForms) {
                RuleProcessPackageRelDO ruleProcessPackageRelDOInsert = new RuleProcessPackageRelDO();
                ruleProcessPackageRelDOInsert.setRuleProcessId(processId);
                ruleProcessPackageRelDOInsert.setRulePackageCode(processManagePackageForm.getPackageCode());
                ruleProcessPackageRelDOInsert.setRulePackageOrderedNumber(processManagePackageForm.getOrderedNumber());
                ruleProcessPackageRelDOInsert.setFlag(CommonConstant.NORMAL_FLAG);
                ruleProcessPackageRelDOS.add(ruleProcessPackageRelDOInsert);
            }
            ruleProcessPackageRelJpaDAO.save(ruleProcessPackageRelDOS);

        }else{
            //没有传，认为删除规则流程下所有规则包
            ruleProcessPackageRelJpaDAO.updateFlag(processId,CommonConstant.DELETE_FLAG);
        }
        return true;
    }

    /**
     * 规则包管理查看
     * @param processPackageInfoForm
     * @return
     */
    public List<RuleProcessPackageVO> processPackage(ProcessPackageInfoForm processPackageInfoForm){
        List<RuleProcessPackageVO> packageList = ruleProcessMapper.packageSearch(processPackageInfoForm.getProcessId(),CommonConstant.NORMAL_FLAG,PublishStatus.published);
        return packageList;
    }

}
