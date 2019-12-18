package com.graduation.project.risk.project.dal.jpa.dataobject.pp;

import com.graduation.project.risk.project.web.form.blacklist.BlacklistSearchForm;
import com.graduation.project.risk.project.web.form.pp.PpSystemVariableInterfaceForm;
import com.graduation.project.risk.project.web.vo.blacklist.BlacklistSearchVO;
import com.graduation.project.risk.common.core.biz.CommonUtil;
import com.graduation.project.risk.project.biz.dto.RiskOrderDTO;
import com.graduation.project.risk.project.biz.manager.BlacklistManager;
import com.graduation.project.risk.project.biz.manager.PpSystemVariableInterfaceManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;

/**
 * @Description pp业务系统变量-每个变量的内容获取---部分内容
 **/
@Data
@Component
public class PpSystemVariableBO implements Serializable {

    //计算数据，变量赋值。   从下面的DTO中得到以及计算

    //------------------基础变量，可直接获得-----------------//
    //年龄
    private Integer pAge; //no

    //婚姻状况--TODO
    private String pMaritalStatus;//1:divorce，2：married，3：secondary_married，4：single, 5:widowed

    //性别
    private Integer pGender;//1：male，2：female

    //职位级别--TODO
    private String pTitle;//1:director，2:entrepreneur, 3:executive, 4:manager, 5:professional, 6:staff, 7:student

    //月薪
    private String pMonthlySalary;

    //申请日期
    private Date pApplicationDate;

    //发薪日期
    private String pPayday;

    //已入职时长
    private Long pEmploymentLength;

    //入职时间
    private Date pEmploymentDate;

    //办公所在市
    private String pOfficeCity;

    //居住所在市
    private String pResidenceCity;

    //申请借款时间
    private Date pApplyTime;


    //------------------衍生变量，需通过计算获得------------------//
    //历史逾期天数
    private Long pDaysOverdue;

    //历史逾期次数
    private Integer pTimesOverdue;

    //本人-内部黑名单
    private Integer pApplicantIsOnTheBlacklist;//1:命中，2：未命中

    //紧急联系人命中内部黑名单数量
    private Integer pEmergencyContactIsOnTheBlacklist;

    //父母-黑名单
    private Integer pParentsIsOnTheBlacklist;//1:命中，2：未命中，0：未填写

    //配偶-黑名单
    private Integer pSpouseIsOnTheBlacklist;//1:命中，2：未命中，0：未填写

    //同一设备当天申请数
    private Integer pApplicationsWithSameDeviceIDOnTheSameDay;

    //同一设备登录账户数
    private Integer pAccountsLoggedInWithSameDeviceID;

    //紧急联系人号码关联用户数
    private Integer pRelevanceAmongUrgentContacts;

    //邮箱关联用户数
    private Integer pUsersWithSameEmail;

    //还款后24小时内申请借款次数
    private Integer pRenewalApplicatioinsWithin24HoursAfterRepayment;

    //紧急联系人未填写配偶
    private Integer pSpouseIsNotInTheEmergenceContactList;//1:命中，2：未命中

    @Autowired
    protected BlacklistManager blacklistManager;

    @Autowired
    protected PpSystemVariableInterfaceManager ppSystemVariableInterfaceManager;

    private static PpSystemVariableBO ppSystemVariableBO;

    @PostConstruct
    public void init(){
        ppSystemVariableBO = this;
        ppSystemVariableBO.blacklistManager = this.blacklistManager;
        ppSystemVariableBO.ppSystemVariableInterfaceManager = this.ppSystemVariableInterfaceManager;
    }

    public PpSystemVariableBO() {

    }

    public PpSystemVariableBO(RiskOrderDTO riskOrderDTO) {

        //年龄--TODO 计算规则以及ktpNo格式不知道
        this.pAge = Integer.valueOf(riskOrderDTO.getKtpNo().substring(0,2));

        //婚姻状况--TODO
        String maritalStatus = riskOrderDTO.getMaritalStatus();
        if (!CommonUtil.isEmpty(maritalStatus)){
            switch (maritalStatus){
                case "divorce":
                    this.pMaritalStatus = "1";
                    break;
                case "married":
                    this.pMaritalStatus = "2";
                    break;
                case "secondary_married":
                    this.pMaritalStatus = "3";
                    break;
                case"single":
                    this.pMaritalStatus = "4";
                    break;
                case "widowed":
                    this.pMaritalStatus = "5";
                    break;
            }
        }

        //性别
        String gender = riskOrderDTO.getGender();
        if (!CommonUtil.isEmpty(gender)){
            switch (gender){
                case "male":
                    this.pGender = 1;
                    break;
                case "female":
                    this.pGender = 2;
                    break;
            }
        }

        //职位级别--TODO
        String level = riskOrderDTO.getJobLevel();
        if(!CommonUtil.isEmpty(level)){
            switch (level){
                case "director":
                    this.pTitle = "1";
                    break;
                case "entrepreneur":
                    this.pTitle = "2";
                    break;
                case "executive" :
                    this.pTitle = "3";
                    break;
                case "manager":
                    this.pTitle = "4";
                    break;
                case "professional" :
                    this.pTitle = "5";
                    break;
                case "staff" :
                    this.pTitle = "6";
                    break;
                case "student" :
                    this.pTitle = "7";
                    break;
            }
        }

        //月薪
        this.pMonthlySalary = riskOrderDTO.getMonthlySalary();

        //申请日期
        this.pApplicationDate = riskOrderDTO.getApplicationTime();

        //发薪日期
        this.pPayday = riskOrderDTO.getSalaryDay();

        //入职时间--TODO
        this.pEmploymentDate = riskOrderDTO.getStartOfEmployment();

        //已入职时长--TODO
        Date now = new Date();
        this.pEmploymentLength = ((now.getTime() - this.pEmploymentDate.getTime()) / (24 * 60 * 60 * 1000));

        //办公所在市
        this.pOfficeCity = riskOrderDTO.getOccupationCity();

        //居住所在市
        this.pResidenceCity = riskOrderDTO.getPersonCity();

        //申请借款时间
        this.pApplyTime = riskOrderDTO.getApplicationTime();

        //历史逾期天数--TODO
        PpSystemVariableInterfaceForm interfaceForm = new PpSystemVariableInterfaceForm();
        interfaceForm.setKtpNo(riskOrderDTO.getKtpNo());
        Map overdueMap = new HashMap<String,Long>();
        overdueMap = ppSystemVariableBO.ppSystemVariableInterfaceManager.overdue(interfaceForm);
        String day = String.valueOf(overdueMap.get("days"));
        Long days = Long.valueOf(day);
        this.pDaysOverdue = days;

        //历史逾期次数————TODO
        String time = String.valueOf(overdueMap.get("times"));
        Integer times = Integer.valueOf(time);
        this.pTimesOverdue = times;

        //本人-内部黑名单
        this.pApplicantIsOnTheBlacklist = 1;
        String ktpNo = riskOrderDTO.getKtpNo();
        String mobile = riskOrderDTO.getMobile();
        BlacklistSearchForm blk = new BlacklistSearchForm();
        blk.setIdNumber(ktpNo);
        blk.setPhoneNumber(mobile);
        Set<BlacklistSearchVO> number = ppSystemVariableBO.blacklistManager.blacklistSearch(blk);
        if (null == number || number.size() == 0) {
            this.pApplicantIsOnTheBlacklist = 2;
        }

        //紧急联系人命中内部黑名单数量
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        Map<String,String> map = new HashMap<String, String>();
        boolean flag = true;
        while(flag){
            if (!CommonUtil.isEmpty(riskOrderDTO.getContactName1())){
                map.put("name0",riskOrderDTO.getContactName1());
                map.put("phone0",riskOrderDTO.getContactPhone1());
                map.put("relation0",riskOrderDTO.getContactRelation1());
                list.add(map);
            }
            else{
                flag = false;
                break;
            }
            if (!CommonUtil.isEmpty(riskOrderDTO.getContactName2())){
                map.put("name1",riskOrderDTO.getContactName2());
                map.put("phone1",riskOrderDTO.getContactPhone2());
                map.put("relation1",riskOrderDTO.getContactRelation2());
                list.add(map);
            }
            else{
                flag = false;
                break;
            }
            if (!CommonUtil.isEmpty(riskOrderDTO.getContactName3())){
                map.put("name2",riskOrderDTO.getContactName3());
                map.put("phone2",riskOrderDTO.getContactPhone3());
                map.put("relation2",riskOrderDTO.getContactRelation3());
                list.add(map);
            }
            else{
                flag = false;
                break;
            }
            if (!CommonUtil.isEmpty(riskOrderDTO.getContactName4())){
                map.put("name3",riskOrderDTO.getContactName4());
                map.put("phone3",riskOrderDTO.getContactPhone4());
                map.put("relation3",riskOrderDTO.getContactRelation4());
                list.add(map);
            }
            else{
                flag = false;
                break;
            }
            if (!CommonUtil.isEmpty(riskOrderDTO.getContactName5())){
                map.put("name4",riskOrderDTO.getContactName5());
                map.put("phone4",riskOrderDTO.getContactPhone5());
                map.put("relation4",riskOrderDTO.getContactRelation5());
                list.add(map);
            }
            flag = false;

        }

        Integer contact = 0;
        for (int i = 0 ; i < list.size(); i++){
            BlacklistSearchForm blklist = new BlacklistSearchForm();
            String phone = "phone" + i;
            blklist.setPhoneNumber(list.get(i).get(phone));
            Set<BlacklistSearchVO> hit = ppSystemVariableBO.blacklistManager.blacklistSearch(blklist);
            if (hit.size() > 0){
                contact++;
            }
        }
        this.pEmergencyContactIsOnTheBlacklist = contact;

        //父母-黑名单--TODO
        Integer parents = 0;
        for (int i = 0 ; i < list.size(); i++){
            String relation = "relation" + i;
            if (list.get(i).get(relation).equals("Parents")){
                BlacklistSearchForm blklist = new BlacklistSearchForm();
                String phone = "phone" + i;
                blklist.setPhoneNumber(list.get(i).get(phone));
                Set<BlacklistSearchVO> hit =ppSystemVariableBO.blacklistManager.blacklistSearch(blklist);
                if (hit.size() > 0){
                    parents = 1;
                    break;
                }
                parents = 2;
            }
        }
        this.pParentsIsOnTheBlacklist = parents;

        //配偶-黑名单
        Integer spouse = 0;
        for (int i = 0 ; i < list.size(); i++){
            String relation = "relation" + i;
            if (list.get(i).get(relation).equals("Spouse")){
                BlacklistSearchForm blklist = new BlacklistSearchForm();
                String phone = "phone" + i;
                blklist.setPhoneNumber(list.get(i).get(phone));
                Set<BlacklistSearchVO> hit = ppSystemVariableBO.blacklistManager.blacklistSearch(blklist);
                if (hit.size() > 0){
                    spouse = 1;
                    break;
                }
                spouse = 2;
            }
        }
        this.pSpouseIsOnTheBlacklist = spouse;

        //同一设备当天申请数--TODO
        interfaceForm.setDeviceId(riskOrderDTO.getDeviceId());
        this.pApplicationsWithSameDeviceIDOnTheSameDay = ppSystemVariableBO.ppSystemVariableInterfaceManager.applicationsWithSameDeviceIDOnTheSameDay(interfaceForm);

        //同一设备登录账户数--TODO
        this.pAccountsLoggedInWithSameDeviceID = ppSystemVariableBO.ppSystemVariableInterfaceManager.accountsLoggedInWithSameDeviceID(interfaceForm);

        //紧急联系人号码关联用户数--TODO
        List<String> mobiles = new ArrayList<>();
        for (int i = 0 ; i < list.size(); i++){
            String phone = "phone" + i;
            mobiles.add(list.get(i).get(phone));
        }

        interfaceForm.setComtactsMobile(mobiles);
        this.pRelevanceAmongUrgentContacts = ppSystemVariableBO.ppSystemVariableInterfaceManager.relevanceAmongUrgentContacts(interfaceForm);

        //邮箱关联用户数--TODO
        interfaceForm.setEmail(riskOrderDTO.getEmail());
        this.pUsersWithSameEmail = ppSystemVariableBO.ppSystemVariableInterfaceManager.usersWithSameEmail(interfaceForm);

//        //还款后24小时内申请借款次数--TODO   没参数会报空指针异常
//        interfaceForm.setOrderCode(riskOrderDTO.getOrderCode());
//        this.pRenewalApplicatioinsWithin24HoursAfterRepayment = ppSystemVariableBO.ppSystemVariableInterfaceManager.renewalApplicatioinsWithin24HoursAfterRepayment(interfaceForm);

        //紧急联系人未填写配偶
        this.pSpouseIsNotInTheEmergenceContactList = (spouse == 0 ? 1 : 2);



    }
}
