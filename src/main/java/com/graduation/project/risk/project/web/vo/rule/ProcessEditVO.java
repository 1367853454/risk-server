package com.graduation.project.risk.project.web.vo.rule;

import com.graduation.project.risk.project.biz.enums.TriggerLinkEnums;
import lombok.Data;

@Data
public class ProcessEditVO {

    private String businessName;

    private String processName;

    private String processCode;

    private TriggerLinkEnums  triggerLink;

    private String processDescribe;

    private Long processId;

}
