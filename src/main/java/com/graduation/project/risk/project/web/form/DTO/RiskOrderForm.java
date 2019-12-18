package com.graduation.project.risk.project.web.form.DTO;

import com.graduation.project.risk.project.biz.enums.TriggerLinkEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RiskOrderForm {

    @ApiModelProperty(value = "订单号",example = "00021531222012270018")
    private String orderCode;

    @ApiModelProperty(value = "该订单产生时间,时间戳",example = "1531299906765")
    private Double applicationTime;

    @ApiModelProperty(value = "发薪日",example = "15号")
    private String salaryDay;

    @ApiModelProperty(value = "设备唯一标识号",example = "mac-mac-mac-mac-212-212-211")
    private String deviceId;

    @ApiModelProperty(value = "申请金额",example = "30000")
    private String applicationAmount;

    @ApiModelProperty(value = "当前所在省",example = "BALI")
    private String personProvince;

    @ApiModelProperty(value = "当前所在市",example = "KABUPATEN BADUNG")
    private String personCity;

    @ApiModelProperty(value = "当前所在区",example = "ABIANSEMAL")
    private String personDistrict;

    @ApiModelProperty(value = "公司名称",example = "hdhjd")
    private String companyName;

    @ApiModelProperty(value = "工作职称",example = "hdhnd")
    private String jobTitle;

    @ApiModelProperty(value = "工作级别",example = "manager")
    private String jobLevel;

    @ApiModelProperty(value = "工作类别",example = "Designer/Architect")
    private String jobType;

    @ApiModelProperty(value = "月薪",example = "4000000-6000000")
    private String monthlySalary;

    @ApiModelProperty(value = "月收入区间最小值",example = "4000000")
    private String lowestMonthlySalary;

    @ApiModelProperty(value = "月收入区间最大值",example = "6000000")
    private String highestMonthlySalary;

    @ApiModelProperty(value = "入职时间，时间戳",example = "1531299906765")
    private Double startOfEmployment;

    @ApiModelProperty(value = "工作所在省",example = "DKI JAKARTA")
    private String occupationProvince;

    @ApiModelProperty(value = "工作所在市",example = "KOTA JAKARTA TIMUR")
    private String occupationCity;

    @ApiModelProperty(value = "工作所在区",example = "JATINEGARA")
    private String occupationDistrict;//工作所在区

    @ApiModelProperty(value = "经度",example = "120.3265192174")
    private String lng;//经度

    @ApiModelProperty(value = "维度",example = "30.12385691345")
    private String lat;//纬度

    @ApiModelProperty(value = "申请人身份证",example = "3515164402860002")
    private String ktpNo;//申请人身份证

    @ApiModelProperty(value = "申请人手机号",example = "81000003")
    private String mobile;//申请人手机号

    @ApiModelProperty(value = "申请人婚姻状态",example = "married")
    private String maritalStatus;//申请人婚姻状态

    @ApiModelProperty(value = "申请人性别",example = "female")
    private String gender;//申请人性别

    @ApiModelProperty(value = "申请人所填邮箱",example = "fei.song@huijie-inc.com")
    private String email;//申请人所填邮箱

    @ApiModelProperty(value = "申请人最早学历",example = "Bachelor")
    private String education;//申请人最高学历

    @ApiModelProperty(value = "申请人真实姓名",example = "fei song")
    private String fullName;//申请人真实姓名

    @ApiModelProperty(value = "申请人宗教信仰",example = "Christianity")
    private String religion;//申请人宗教信仰

    @ApiModelProperty(value = "1号联系人名字",example = "da xiong")
    private String contactName1;//1号联系人名字

    @ApiModelProperty(value = "1号联系人手机号",example = "85555555")
    private String contactPhone1;//1号联系人手机号

    @ApiModelProperty(value = "1号联系人与申请人的关系",example = "Parents")
    private String contactRelation1;//1号联系人与申请人关系

    @ApiModelProperty("king")
    private String contactName2;

    @ApiModelProperty(example = "13656668567")
    private String contactPhone2;

    @ApiModelProperty(example = "Parents")
    private String contactRelation2;

    @ApiModelProperty(example = "惠借-靖思hr")
    private String contactName3;

    @ApiModelProperty(example = "13877777777")
    private String contactPhone3;

    @ApiModelProperty(example = "Spouse")
    private String contactRelation3;

    @ApiModelProperty(example = "车润")
    private String contactName4;

    @ApiModelProperty(example = "15967124463")
    private String contactPhone4;

    @ApiModelProperty(example = "Parents")
    private String contactRelation4;

    @ApiModelProperty(example = "你猜")
    private String contactName5;

    @ApiModelProperty(example = "233343333")
    private String contactPhone5;

    @ApiModelProperty(example = "friends")
    private String contactRelation5;

    @ApiModelProperty(value = "流畅Code",example = "chushen")
    private String processCode;//流程代码

    @ApiModelProperty(value = "业务Code",example = "100")
    private String businessCode;//业务代码

    @ApiModelProperty(value = "触发环节",example = "firstInspection")
    private TriggerLinkEnums triggerLink;//触发环节

    @ApiModelProperty(value = "还款期限",example = "12月")
    private String termOfLoan;//还款期限
}
