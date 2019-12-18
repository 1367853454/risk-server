package com.graduation.project.risk.project.biz.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RiskOrderDTO {

    private String orderCode; //订单号

    private Date applicationTime;//该订单产生时间

    private String businessCode; //订单的所属业务id

    private String salaryDay;//发薪日

    private String deviceId;//设备唯一标识号

    private String applicationAmount;//申请金额

    private String personProvince;//当前所在省

    private String personCity;//当前所在市

    private String personDistrict;//当前所在区

    private String companyName;//公司名称

    private String jobTitle;//工作职称

    private String jobLevel;//工作级别

    private String jobType;//工作类型

    private String monthlySalary;//月薪

    private String lowestMonthlySalary;//月收入区间最小值

    private String highestMonthlySalary;//月收入区间最大值

    private Date startOfEmployment;//入职时间

    private String occupationProvince;//工作所在省

    private String occupationCity;//工作所在市

    private String occupationDistrict;//工作所在区

    private String lng;//经度

    private String lat;//纬度

    private String ktpNo;//申请人身份证

    private String mobile;//申请人手机号

    private String maritalStatus;//申请人婚姻状态

    private String gender;//申请人性别

    private String email;//申请人所填邮箱

    private String education;//申请人最高学历

    private String fullName;//申请人真实姓名

    private String religion;//申请人宗教信仰

    private String contactName1;//1号联系人名字

    private String contactPhone1;//1号联系人手机号

    private String contactRelation1;//1号联系人与申请人关系

    private String contactName2;

    private String contactPhone2;

    private String contactRelation2;

    private String contactName3;

    private String contactPhone3;

    private String contactRelation3;

    private String contactName4;

    private String contactPhone4;

    private String contactRelation4;

    private String contactName5;

    private String contactPhone5;

    private String contactRelation5;

    private String termOfLoan; //借款期限





}
