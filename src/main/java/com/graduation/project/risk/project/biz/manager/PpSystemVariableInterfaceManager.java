package com.graduation.project.risk.project.biz.manager;

import com.graduation.project.risk.common.core.biz.BizCoreException;
import com.graduation.project.risk.common.core.biz.ErrorCode;
import com.graduation.project.risk.project.dal.jpa.dao.pp.PpRiskLoanInfoJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.pp.PpRiskLoanStatusJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRiskLoanInfoDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRiskLoanStatusDO;
import com.graduation.project.risk.project.dal.mybaits.dao.PpSystemVariableInterfaceMapper;
import com.graduation.project.risk.project.web.form.pp.PpSystemVariableInterfaceForm;
import com.graduation.project.risk.project.biz.enums.OverdueStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PpSystemVariableInterfaceManager {

    private Logger logger = LoggerFactory.getLogger(PpSystemVariableInterfaceManager.class);

    @Autowired
    private PpRiskLoanStatusJpaDAO ppRiskLoanStatusJpaDAO;

    @Autowired
    private PpRiskLoanInfoJpaDAO ppRiskLoanInfoJpaDAO;

    @Autowired
    private PpSystemVariableInterfaceMapper ppSystemVariableInterfaceMapper;

    /*
     *历史逾期次数 历史逾期天数
     * @param PpSystemVariableInterfaceForm
     * @return Map
     */
    public Map<String,Long> overdue(PpSystemVariableInterfaceForm ppSystemVariableInterfaceForm){
        List<PpRiskLoanInfoDO>  all = ppRiskLoanInfoJpaDAO.findAllByKtpNo(ppSystemVariableInterfaceForm.getKtpNo());
        List<String> orderCode = new ArrayList<>();
        for (PpRiskLoanInfoDO pp : all){
            orderCode.add(pp.getOrderCode());
        }

        //逾期 + 逾期已还清   一个订单号只有一个状态
        Long times = 0L;
        Long days = 0L;
        for (String overdueTimes : orderCode){
            PpRiskLoanStatusDO status = new PpRiskLoanStatusDO();
            status = ppRiskLoanStatusJpaDAO.findByOrderCodeAndOverdueStatus(overdueTimes, OverdueStatus.overduePayment);
            if (null != status){
                Date repaymentTime = status.getRepaymentTime();
                Date overdueTime = status.getOverdueTime();
                days += ((repaymentTime.getTime() - overdueTime.getTime()) / (24 * 60 * 60 * 1000));
                times++;
                break;
            }
            status = ppRiskLoanStatusJpaDAO.findByOrderCodeAndOverdueStatus(overdueTimes, OverdueStatus.overdue);
            if (null != status){
                Date now = new Date();
                Date overdueTime = status.getOverdueTime();
                days += ((now.getTime() - overdueTime.getTime()) / (24 * 60 * 60 * 1000));
                times++;
                break;
            }
        }

        Map map= new HashMap<String,Long>();
        map.put("times",times);
        map.put("days",days);

        return map;
    }
    /*
    *同一设备当天申请数
    * @param PpSystemVariableInterfaceForm
    * @return Integer
     */
    public Integer applicationsWithSameDeviceIDOnTheSameDay(PpSystemVariableInterfaceForm ppSystemVariableInterfaceForm){
        String deviceId = ppSystemVariableInterfaceForm.getDeviceId();
        Integer account = ppSystemVariableInterfaceMapper.applicationsWithSameDeviceIDOnTheSameDay(deviceId);

        return account;
    }
    /*
    *同一设备登录账户数
    *@param PpSystemVariableInterfaceForm
    * @return Integer
     */
    public Integer accountsLoggedInWithSameDeviceID(PpSystemVariableInterfaceForm ppSystemVariableInterfaceForm){
        String deviceId = ppSystemVariableInterfaceForm.getDeviceId();
        Integer account = ppSystemVariableInterfaceMapper.accountsLoggedInWithSameDeviceID(deviceId);

        return account;
    }
    /*
    *紧急联系人号码关联用户数
    *@param PpSystemVariableInterfaceForm
    * @return Integer
     */
    public Integer relevanceAmongUrgentContacts(PpSystemVariableInterfaceForm ppSystemVariableInterfaceForm){
        List<String> contacts = ppSystemVariableInterfaceForm.getComtactsMobile();
        Integer account = ppSystemVariableInterfaceMapper.relevanceAmongUrgentContacts(contacts);

        return account;
    }
    /*
    *邮箱关联用户数
    * @param PpSystemVariableInterfaceForm
    * @return Integer
     */
    public Integer usersWithSameEmail(PpSystemVariableInterfaceForm ppSystemVariableInterfaceForm){
        String email = ppSystemVariableInterfaceForm.getEmail();
        Integer account = ppSystemVariableInterfaceMapper.usersWithSameEmail(email);

        return account;
    }
    /*
    *还款后24小时内申请借款次数
    * @param PpSystemVariableInterfaceForm
    * @return Integer
     */
    public Integer renewalApplicatioinsWithin24HoursAfterRepayment(PpSystemVariableInterfaceForm ppSystemVariableInterfaceForm){
        String orderCode = ppSystemVariableInterfaceForm.getOrderCode();
        String ktpNo = ppSystemVariableInterfaceForm.getKtpNo();

        PpRiskLoanStatusDO ppRiskLoanStatusDO = new PpRiskLoanStatusDO();
        ppRiskLoanStatusDO = ppRiskLoanStatusJpaDAO.findByOrderCode(orderCode);
        Date repayment_time = ppRiskLoanStatusDO.getRepaymentTime();
        if (repayment_time == null){
            throw new BizCoreException(ErrorCode.NO_REPAYMENT_HAS_YET_BEEN_MADE);
        }

        Integer account = ppSystemVariableInterfaceMapper.renewalApplicatioinsWithin24HoursAfterRepayment(orderCode,ktpNo);

        return account;
    }


}
