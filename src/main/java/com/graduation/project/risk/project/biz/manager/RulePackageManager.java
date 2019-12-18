package com.graduation.project.risk.project.biz.manager;

import com.github.pagehelper.PageHelper;
import com.graduation.project.risk.common.core.biz.*;
import com.graduation.project.risk.project.dal.jpa.dao.rule.*;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.*;
import com.graduation.project.risk.project.dal.mybaits.dao.RulePackageMapper;
import com.graduation.project.risk.project.web.form.rule.*;
import com.graduation.project.risk.project.web.vo.KeyValueVO;
import com.graduation.project.risk.project.web.vo.rule.RulePackageVO;
import com.graduation.project.risk.project.web.vo.rule.RulePackageVersionVO;
import com.graduation.project.risk.project.web.vo.rule.RuleVO;
import com.graduation.project.risk.common.core.dal.mongdb.query.PageUtil;
import com.graduation.project.risk.common.model.Page;
import com.graduation.project.risk.project.biz.constants.CommonConstant;
import com.graduation.project.risk.project.biz.enums.CommonRuleEnums;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import com.graduation.project.risk.project.biz.enums.PublishStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RulePackageManager {

    private Logger logger = LoggerFactory.getLogger(RulePackageManager.class);

    @Autowired
    private RulePackageMapper rulePackageMapper;
    @Autowired
    private RuleBusinessJpaDAO ruleBusinessJpaDAO;
    @Autowired
    private RulePackageJpaDAO rulePackageJpaDAO;
    @Autowired
    private RuleJpaDAO ruleJpaDAO;
    @Autowired
    private RuleConditionJpaDAO ruleConditionJpaDAO;
    @Autowired
    private RuleHandleJpaDAO ruleHandleJpaDAO;
    @Autowired
    private RuleVariableJpaDAO ruleVariableJpaDAO;
    @Autowired
    private RulePackageManager rulePackageManager;
    @Autowired
    private RuleProcessPackageRelJpaDAO ruleProcessPackageRelJpaDAO;

    /*
     *规则包列表页查询
     *
     * @param RulePackageForm
     * @return
     *
     */
    public Page<RulePackageVO> rulePackageSearch(RulePackageForm rulePackageForm){
        PageHelper.startPage(rulePackageForm.getPageNumber(),rulePackageForm.getPageSize());
        String packageCode = rulePackageForm.getPackageCode();
        String packageName = rulePackageForm.getPackageName();
        String businessCode = rulePackageForm.getBusinessCode();
        if (StringUtil.isEmpty(packageCode) && StringUtil.isEmpty(packageName) && StringUtil.isEmpty(businessCode)){
            throw new BizCoreException(ErrorCode.PARAM_MISS);
        }

        List<RulePackageVO> packageVOS = rulePackageMapper.packageSearch(packageCode,packageName,businessCode, CommonConstant.NORMAL_FLAG);

        Page<RulePackageVO> page = PageUtil.toPage(packageVOS);

        return page;
    }

    public String getVersion(String packageCode){

        List<RulePackageDO> version = rulePackageJpaDAO.findByPackageCodeAndFlag(packageCode, CommonConstant.NORMAL_FLAG);
        if (CommonUtil.isEmpty(version)){
            return null;
        }

        String nextVersion = nextVersion(version.get(version.size()-1).getPackageVersion());

        return nextVersion;
    }

    /*
    *版本号自动增加
    * @param String
    * @return
     */
    private static String nextVersion(String version){
        int size = version.length() + 1 ;
        int [] a = new int[size];
        a[0] = 0;//保留的一位可能进位的值
        int i = 1;
        for(String retval : version.split("\\.")){
            a[i] = Integer.parseInt(retval);
            i++;
        }

        for(int j = i-1; j >= 0; j--){
            if(a[j] >= 9){
                a[j] =0;
            }
            else{
                a[j]++;
                break;
            }
        }

        String str = "";

        for (int j = 0 ; j < i; j++){
            if(j==0 && a[j] == 0)
                continue;
            str += String.valueOf(a[j]);
            if (j < i-1){
                str += ".";
            }
        }

        return str;
    }


    /**
     * add rule package
     * @param packageAddForm
     * @return
     */
    public boolean rulePackageAdd(PackageAddForm packageAddForm) {
        //校验业务code是否正确
        String businessCode = packageAddForm.getBusinessCode();
        RuleBusinessDO ruleBusinessDO = ruleBusinessJpaDAO.findByBusinessCodeAndStatus(businessCode, CommonStatus.normal);
        if (CommonUtil.isEmpty(ruleBusinessDO)) {
            throw new BizCoreException(ErrorCode.BUSINESS_CODE_NOT_EXISTED);
        }

        //规则包代码不能重复
        if (!CommonUtil.isEmpty(rulePackageJpaDAO.findByBusinessCodeAndPackageCodeAndFlag(businessCode, packageAddForm.getPackageCode(),CommonConstant.NORMAL_FLAG))) {
            throw new BizCoreException(ErrorCode.PACKAGE_CODE_IS_EXISTED);
        }

        RulePackageDO rulePackageDO = new RulePackageDO();
        rulePackageDO.setBusinessName(ruleBusinessDO.getBusinessName());
        rulePackageDO.setBusinessCode(businessCode);
        rulePackageDO.setPackageName(packageAddForm.getPackageName());
        rulePackageDO.setPackageCode(packageAddForm.getPackageCode());
        if (!CommonUtil.isEmpty(packageAddForm.getDescription())){
            rulePackageDO.setPackageDescribe(packageAddForm.getDescription());
        }
        rulePackageDO.setPackageVersion(CommonRuleEnums.RULE_PACKAGE_DEFAULT_VERSION.getCode());
        rulePackageDO.setPublishStatus(PublishStatus.unpublished);
        rulePackageDO.setFlag(CommonConstant.NORMAL_FLAG);//1：正常；-1：删除

        rulePackageJpaDAO.save(rulePackageDO);

        return true;
    }

    /**
     * delete rule package
     * @param packageDeleteForm
     * @return
     */
    public boolean rulePackageDelete(PackageDeleteForm packageDeleteForm) {
        Long packageId = packageDeleteForm.getPackageId();
        RulePackageDO rulePackageDO = rulePackageJpaDAO.findByIdAndFlag(packageId,CommonConstant.NORMAL_FLAG);
        if (CommonUtil.isEmpty(rulePackageDO)){
            throw new BizCoreException(ErrorCode.PACKAGE_INFO_NOT_EXISTED);
        }

        if (PublishStatus.published.equals(rulePackageDO.getPublishStatus())){
            throw new BizCoreException(ErrorCode.PACKAGE_IS_PUBLISH);
        }

        List<RuleDO> ruleDOList = ruleJpaDAO.findByRulePackageIdAndFlag(packageId, CommonConstant.NORMAL_FLAG);
        if (!CommonUtil.isEmpty(ruleDOList)){
            for (RuleDO rule : ruleDOList) {
                rule.setFlag(CommonConstant.DELETE_FLAG);
                ruleJpaDAO.save(rule);
            }
        }

        rulePackageDO.setFlag(CommonConstant.DELETE_FLAG);
        rulePackageJpaDAO.save(rulePackageDO);
        return true;
    }


    /**
     * 规则包编辑，每次如果有更新，生成新的数据，不修改原数据（每次有更新，都生成一个新的版本）
     * @param packageUpdateForm
     * @return
     */
    public boolean rulePackageUpdate(PackageUpdateForm packageUpdateForm) {
        logger.debug("规则包更新传入参数"+packageUpdateForm);
        //判断更新的数据和原数据是否相同，相同则不做更新，不同则更新并生成新的规则包版本号
        String packageCode = packageUpdateForm.getPackageCode();
        String packageVersion = packageUpdateForm.getPackageVersion();
        PackageUpdateForm packageUpdateFormOld = getOldPackageByIdAndVersion(packageCode,packageVersion);
        logger.debug("规则包更新，判断是否更改，查询出来的结果"+packageUpdateFormOld);

        if (MD5Util.encode(packageUpdateForm.toString()).equals(MD5Util.encode(packageUpdateFormOld.toString()))){
            logger.debug("rulePackageUpdate---no update");
            return true;//没有修改，直接返回true
        }else{
            logger.debug("rulePackageUpdate---has update");
            //--------------------------新增规则包begin---------------------------
            RulePackageDO oldRulePackageDO = rulePackageJpaDAO.findByPackageCodeAndPackageVersionAndFlag(packageCode,packageVersion,CommonConstant.NORMAL_FLAG);
            RulePackageDO rulePackageDO = new RulePackageDO();
            rulePackageDO.setFlag(CommonConstant.NORMAL_FLAG);
            rulePackageDO.setPublishStatus(PublishStatus.unpublished);
            String nextVersion = getVersion(packageCode);
            if (CommonUtil.isEmpty(nextVersion)){
                rulePackageDO.setPackageVersion(CommonRuleEnums.RULE_PACKAGE_DEFAULT_VERSION.getCode());
            }else{
                rulePackageDO.setPackageVersion(nextVersion);
            }
            rulePackageDO.setPackageDescribe(oldRulePackageDO.getPackageDescribe());
            rulePackageDO.setPackageCode(oldRulePackageDO.getPackageCode());
            rulePackageDO.setPackageName(oldRulePackageDO.getPackageName());
            rulePackageDO.setBusinessCode(oldRulePackageDO.getBusinessCode());

            rulePackageDO.setBusinessName(ruleBusinessJpaDAO.findByBusinessCodeAndStatus(oldRulePackageDO.getBusinessCode(),CommonStatus.normal).getBusinessName());

            RulePackageDO rulePackageDOResult = rulePackageJpaDAO.save(rulePackageDO);
            if (CommonUtil.isEmpty(rulePackageDOResult)){
                logger.error("rulePackageUpdate.rulePackageJpaDAO.save is error");
                throw new BizCoreException(ErrorCode.SYSTEM_EXCEPTION);
            }
            //--------------------------新增规则包end---------------------------


            //--------------------------新增规则begin---------------------------
            Long packageId = rulePackageDOResult.getId();
            List<PackageUpdateRuleForm> packageUpdateRuleForms = packageUpdateForm.getPackageUpdateRuleForms();
            if (!CommonUtil.isEmpty(packageUpdateRuleForms)){
                for (PackageUpdateRuleForm packageUpdateRuleForm : packageUpdateRuleForms) {
                    RuleDO ruleDO = new RuleDO();
                    ruleDO.setExecuteCondition(CommonRuleEnums.convertFrom(packageUpdateRuleForm.getExecuteCondition()));
                    ruleDO.setFlag(CommonConstant.NORMAL_FLAG);
                    ruleDO.setRuleName(packageUpdateRuleForm.getRuleName());
                    ruleDO.setRuleDescribe(packageUpdateRuleForm.getRuleDescribe());
                    ruleDO.setRuleTips(packageUpdateRuleForm.getRuleTips());
                    ruleDO.setStatus(PublishStatus.convertFrom(packageUpdateRuleForm.getStatus()));
                    ruleDO.setRulePackageId(packageId);
                    RuleDO ruleDOResult = ruleJpaDAO.save(ruleDO);
                    Long ruleId = ruleDOResult.getId();

                    //--------------------------新增条件begin---------------------------
                    List<PackageUpdateRuleConditionForm> packageUpdateRuleConditionForms = packageUpdateRuleForm.getPackageUpdateRuleConditionForms();
                    List<RuleConditionDO> ruleConditionDOS = new ArrayList<>();
                    if (!CommonUtil.isEmpty(packageUpdateRuleConditionForms)){
                        for (PackageUpdateRuleConditionForm packageUpdateRuleConditionForm : packageUpdateRuleConditionForms) {
                            RuleConditionDO ruleConditionDO = new RuleConditionDO(packageUpdateRuleConditionForm,ruleId);
                            ruleConditionDOS.add(ruleConditionDO);
                        }
                    }
                    ruleConditionJpaDAO.save(ruleConditionDOS);

                    List<PackageUpdateRuleHandleForm> packageUpdateRuleHandleForms = packageUpdateRuleForm.getPackageUpdateRuleHandleForms();
                    List<RuleHandleDO> ruleHandleDOS = new ArrayList<>();
                    if (!CommonUtil.isEmpty(packageUpdateRuleHandleForms)){
                        for (PackageUpdateRuleHandleForm packageUpdateRuleHandleForm : packageUpdateRuleHandleForms) {
                            RuleHandleDO ruleHandleDO = new RuleHandleDO(packageUpdateRuleHandleForm,ruleId);
                            ruleHandleDOS.add(ruleHandleDO);
                        }
                    }
                    ruleHandleJpaDAO.save(ruleHandleDOS);
                    //--------------------------新增条件end---------------------------
                }
            }
            //--------------------------新增规则end---------------------------

            return true;
        }
    }

    /**
     * 根据规则包代码和规则包版本查找原数据，用于判断是否需要更新规则包并生成规则版本
     * @param packageCode
     * @param packageVersion
     * @return
     */
    private PackageUpdateForm getOldPackageByIdAndVersion(String packageCode, String packageVersion) {
        List<PackageUpdateRuleForm> packageUpdateRuleForms = new ArrayList<>();
        PackageUpdateForm packageUpdateForm = new PackageUpdateForm();
        packageUpdateForm.setPackageCode(packageCode);
        packageUpdateForm.setPackageVersion(packageVersion);

        RulePackageDO rulePackageDO = rulePackageJpaDAO.findByPackageCodeAndPackageVersionAndFlag(packageCode,packageVersion,CommonConstant.NORMAL_FLAG);
        if (CommonUtil.isEmpty(rulePackageDO)){
            logger.error("getOldPackageByIdAndVersion.findByPackageCodeAndPackageVersionAndFlag result is null");
            throw new BizCoreException(ErrorCode.PACKAGE_INFO_NOT_EXISTED);
        }

        Long packageId = rulePackageDO.getId();
        List<RuleDO> ruleDOList = ruleJpaDAO.findByRulePackageIdAndFlag(packageId,CommonConstant.NORMAL_FLAG);
        if (!CommonUtil.isEmpty(ruleDOList)){
            for (RuleDO ruleDO : ruleDOList) {
                Long ruleId = ruleDO.getId();
                PackageUpdateRuleForm packageUpdateRuleForm = new PackageUpdateRuleForm();
                packageUpdateRuleForm.setRuleName(ruleDO.getRuleName());
                packageUpdateRuleForm.setRuleDescribe(ruleDO.getRuleDescribe());
                packageUpdateRuleForm.setRuleTips(ruleDO.getRuleTips());
                packageUpdateRuleForm.setExecuteCondition(ruleDO.getExecuteCondition().getCode());
                packageUpdateRuleForm.setStatus(ruleDO.getStatus().getCode());

                List<PackageUpdateRuleConditionForm> packageUpdateRuleConditionForms = new ArrayList<>();
                List<RuleConditionDO> ruleConditionDOS = ruleConditionJpaDAO.findByRuleId(ruleId);
                if (!CommonUtil.isEmpty(ruleConditionDOS)){
                    for (RuleConditionDO ruleConditionDO : ruleConditionDOS) {
                        PackageUpdateRuleConditionForm packageUpdateRuleConditionForm = new PackageUpdateRuleConditionForm();
                        packageUpdateRuleConditionForm.setVariableId(ruleConditionDO.getRuleVariableId());
                        packageUpdateRuleConditionForm.setCompare(ruleConditionDO.getCompare());
                        packageUpdateRuleConditionForm.setValue(ruleConditionDO.getCompareValue());
                        packageUpdateRuleConditionForms.add(packageUpdateRuleConditionForm);
                    }
                }
                packageUpdateRuleForm.setPackageUpdateRuleConditionForms(packageUpdateRuleConditionForms);

                List<PackageUpdateRuleHandleForm> packageUpdateRuleHandleForms = new ArrayList<>();
                List<RuleHandleDO> ruleConditionResultDOS = ruleHandleJpaDAO.findByRuleId(ruleId);
                if (!CommonUtil.isEmpty(ruleConditionResultDOS)){
                    for (RuleHandleDO ruleConditionResultDO : ruleConditionResultDOS) {
                        PackageUpdateRuleHandleForm packageUpdateRuleHandleForm = new PackageUpdateRuleHandleForm();
                        packageUpdateRuleHandleForm.setHandleVariableId(ruleConditionResultDO.getRuleVariableId());
                        packageUpdateRuleHandleForm.setHandleCompare(ruleConditionResultDO.getCompare());
                        packageUpdateRuleHandleForm.setHandleValue(ruleConditionResultDO.getCompareValue());
                        packageUpdateRuleHandleForms.add(packageUpdateRuleHandleForm);
                    }
                }
                packageUpdateRuleForm.setPackageUpdateRuleHandleForms(packageUpdateRuleHandleForms);
                packageUpdateRuleForms.add(packageUpdateRuleForm);
            }
        }
        packageUpdateForm.setPackageUpdateRuleForms(packageUpdateRuleForms);

        return packageUpdateForm;
    }
    /*
    *发布规则包前检查、选择发布版本
    * @param businessCode packageCode id
    * @return  BusinessName PackageCode PackageVersion
     */
    public RulePackageVO getPackageInfo(PackageReleaseBeforeForm packageReleaseBeforeForm){

        RulePackageDO rulePackageDO1 = rulePackageJpaDAO.findById(packageReleaseBeforeForm.getPackageId());
        RulePackageVO rulePackageVO = new RulePackageVO();
        rulePackageVO.setBusinessName(rulePackageDO1.getBusinessName());
        rulePackageVO.setPackageCode(rulePackageDO1.getPackageCode());
        rulePackageVO.setPackageVersion(rulePackageDO1.getPackageVersion());

        return rulePackageVO;

    }

    /*
     * 获取版本
     *@param businessCode packageCode
     * @return key=id, value=version
     */
    public List<KeyValueVO> getVersion(PackageVersionForm packageVersionForm) {

        List<KeyValueVO> list = new ArrayList<>();

        List<RulePackageDO> version = rulePackageJpaDAO.findByBusinessCodeAndPackageCodeAndFlag(packageVersionForm.getBusinessCode(),packageVersionForm.getPackageCode(),CommonConstant.NORMAL_FLAG);

        if (version == null || version.size() == 0) {
            return list;
        }

        for (RulePackageDO ver : version) {
            list.add(new KeyValueVO(ver.getId().toString(),ver.getPackageVersion()));
        }

        return list;
    }

    /**
     * 获取规则包版本及其发布状态
     * @param packageVersionForm
     * @return
     */
    public List<RulePackageVersionVO> getVersionAndStatus(PackageVersionForm packageVersionForm){
        List<RulePackageVersionVO> packageVersionVOList = new ArrayList<>();
        List<RulePackageDO> version = rulePackageJpaDAO.findByBusinessCodeAndPackageCodeAndFlag(packageVersionForm.getBusinessCode(),packageVersionForm.getPackageCode(),CommonConstant.NORMAL_FLAG);

        if (version == null || version.size() == 0) {
            return packageVersionVOList;
        }

        for (RulePackageDO rulePackageDO : version) {
            RulePackageVersionVO packageVersionVO = new RulePackageVersionVO();
            packageVersionVO.setPackageId(rulePackageDO.getId());
            packageVersionVO.setPackageVersion(rulePackageDO.getPackageVersion());
            packageVersionVO.setPublishStatus(rulePackageDO.getPublishStatus().getCode());
            packageVersionVOList.add(packageVersionVO);
        }

        return packageVersionVOList;
    }


    /*
    *获取全部变量
    * @param
    * @return
     */
    public List<KeyValueVO> getVariables(){
        List<KeyValueVO> list = new ArrayList<>();

        List<RuleVariableDO> variables = ruleVariableJpaDAO.findAll();

        if (variables == null || variables.size() == 0){
            return list;
        }
        for (RuleVariableDO variable : variables){
            list.add(new KeyValueVO(variable.getVariableName(),variable.getId().toString()));
        }

        return list;
    }
    /*
    *发布
    * @param PackageReleaseForm
    *
     */
    public void release(PackageReleaseForm packageReleaseForm){
        RulePackageDO rulePackage = rulePackageJpaDAO.findByIdAndFlag(packageReleaseForm.getPackageId(),CommonConstant.NORMAL_FLAG);
        if (null == rulePackage){
            throw new BizCoreException(ErrorCode.NO_RECORD);
        }

        String BusinessCode = rulePackage.getBusinessCode();
        String PackageCode = rulePackage.getPackageCode();
        String packageVersion = packageReleaseForm.getPackageVersion();

        rulePackage =  rulePackageJpaDAO.findByBusinessCodeAndPackageCodeAndPackageVersionAndFlag(BusinessCode, PackageCode, packageVersion, CommonConstant.NORMAL_FLAG);
        if (null == rulePackage){
            throw new BizCoreException(ErrorCode.NO_RECORD);
        }

        RulePackageDO rulePackageDO = rulePackageJpaDAO.findByBusinessCodeAndAndPackageCodeAndPublishStatusAndFlag(BusinessCode,PackageCode,PublishStatus.published,CommonConstant.NORMAL_FLAG);

        if (null != rulePackageDO){
            throw new BizCoreException(ErrorCode.RULE_PACKAGE_ONLINE);
        }

        Integer account = rulePackageMapper.release(BusinessCode,PackageCode,CommonConstant.NORMAL_FLAG);
        if (account > 0){
            throw new BizCoreException(ErrorCode.PACKAGE_BEING_USED);
        }

        rulePackage.setPublishStatus(PublishStatus.published);

        rulePackageJpaDAO.save(rulePackage);

    }
    /*
     *下线
     * @param PackageReleaseForm
     *
     */
    public void offline(PackageOfflineForm packageOfflineForm){
        Long packageId = packageOfflineForm.getPackageId();
        RulePackageDO release = rulePackageJpaDAO.findByIdAndFlag(packageOfflineForm.getPackageId(),CommonConstant.NORMAL_FLAG);
        if (null == release){
            throw new BizCoreException(ErrorCode.NO_RECORD);
        }
        Integer account = rulePackageMapper.beingUsed(packageId,CommonConstant.NORMAL_FLAG,PublishStatus.published);
        if (account > 0){
            throw new BizCoreException(ErrorCode.PACKAGE_BEING_USED);
        }
        release = rulePackageJpaDAO.findById(packageOfflineForm.getPackageId());
        release.setPublishStatus(PublishStatus.unpublished);

        rulePackageJpaDAO.save(release);
    }
    /*
    *规则状态
    * @param PackageStartAndStopForm
    * @return
     */
    public void stopAndStart(PackageStartAndStopForm packageStartAndStopForm){
        RuleDO ruleDO = ruleJpaDAO.findById(packageStartAndStopForm.getRuleId());
        if (null == ruleDO){
            throw new BizCoreException(ErrorCode.NO_RECORD);
        }
        String operation = packageStartAndStopForm.getOperation();
        if (operation.equals("off")) {
            ruleDO.setStatus(PublishStatus.unpublished);
        }
        else{
            ruleDO.setStatus(PublishStatus.published);
        }
        ruleJpaDAO.save(ruleDO);
    }
    /*
    *规则包编辑前查看
    * @param PackageSearchForm PackageCode packageVersion
    * @return
     */
    public List<RuleVO> ruleInfo(PackageSearchForm packageSearchForm){
        String packageCode = packageSearchForm.getPackageCode();
        String version = packageSearchForm.getVersion();
        List<RuleVO> ruleVOList = rulePackageMapper.ruleInfo(packageCode,CommonConstant.NORMAL_FLAG,version);
        return ruleVOList;
    }
    /*
    *删除规则
    * @param ruleId
    *
     */
    public void ruleDelete(RuleDeleteForm ruleDeleteForm){
        RuleDO ruleDO = ruleJpaDAO.findByIdAndFlag(ruleDeleteForm.getRuleId(),CommonConstant.NORMAL_FLAG);
        if (null == ruleDO){
            throw new BizCoreException(ErrorCode.NO_RECORD);
        }
        ruleDO.setFlag(CommonConstant.DELETE_FLAG);
        ruleJpaDAO.save(ruleDO);
    }
    /*
    *判断是否是发布状态 公共方法
     */
    private boolean judge(Long packageId){

        boolean flag = true;
        RulePackageDO rulePackage = rulePackageJpaDAO.findByIdAndFlag(packageId,CommonConstant.NORMAL_FLAG);
        if (null == rulePackage){
            throw new BizCoreException(ErrorCode.NO_RECORD);
        }
        if (rulePackage.getPublishStatus().equals(PublishStatus.published)){
            flag = false;
        }
        return flag;
    }
}
