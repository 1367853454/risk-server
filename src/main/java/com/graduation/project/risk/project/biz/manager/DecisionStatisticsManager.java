package com.graduation.project.risk.project.biz.manager;

import com.graduation.project.risk.project.biz.constants.CommonConstant;
import com.graduation.project.risk.project.biz.enums.DecisionSimulationEnums;
import com.graduation.project.risk.project.biz.enums.PublishStatus;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RuleExcuteRecordJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RulePackageJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.rule.RuleProcessJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RulePackageDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleProcessDO;
import com.graduation.project.risk.project.dal.mybaits.dao.DecisionStatisticsMapper;
import com.graduation.project.risk.project.web.form.decision.StatisticsSearchForm;
import com.graduation.project.risk.project.web.vo.KeyValueVO;
import com.graduation.project.risk.project.web.vo.decision.DecisionStatisticsFunnelVO;
import com.graduation.project.risk.project.web.vo.decision.DecisionStatisticsPackageVO;
import com.graduation.project.risk.project.web.vo.decision.DecisionStatisticsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DecisionStatisticsManager {

    private Logger logger = LoggerFactory.getLogger(DecisionStatisticsManager.class);

    @Autowired
    private RuleExcuteRecordJpaDAO ruleExcuteRecordJpaDAO;

    @Autowired
    private RulePackageJpaDAO rulePackageJpaDAO;

    @Autowired
    private DecisionStatisticsMapper decisionStatisticsMapper;

    @Autowired
    private RuleProcessJpaDAO ruleProcessJpaDAO;

    /*
     *决策统计 下面
     * @param StatisticsSearchForm
     * @return
     */
    public List<DecisionStatisticsVO> statistics(StatisticsSearchForm statisticsSearchForm){
        String startDate = statisticsSearchForm.getStartDate();
        String endDate = statisticsSearchForm.getEndDate();
        String packageCode = statisticsSearchForm.getPackageCode();
        String status = PublishStatus.published.toString();
        String processCode = statisticsSearchForm.getProcessCode();
        List<DecisionStatisticsVO> list = decisionStatisticsMapper.statistics(startDate,endDate,packageCode,CommonConstant.NORMAL_FLAG, status,processCode);

        List<DecisionStatisticsVO> result = new ArrayList<>();

        for (DecisionStatisticsVO get : list){
            if (get.getResult().equals(DecisionSimulationEnums.REJECT.toString())){
                result.add(get);
            }
        }

        return result;
    }
    /*
     *决策统计 上面那个部分
     * @param StatisticsSearchForm
     * @return
     */
    public List<DecisionStatisticsPackageVO> packageVO(StatisticsSearchForm statisticsSearchForm){
        String startDate = statisticsSearchForm.getStartDate();
        String endDate = statisticsSearchForm.getEndDate();
        String packageCode = statisticsSearchForm.getPackageCode();
        String status = PublishStatus.published.toString();
        String processCode = statisticsSearchForm.getProcessCode();
        List<DecisionStatisticsPackageVO> list = decisionStatisticsMapper.packageVO(startDate,endDate,packageCode,status,processCode);

        List<DecisionStatisticsPackageVO> result = new ArrayList<>();

        for (DecisionStatisticsPackageVO get : list){
            if (get.getResult().equals(DecisionSimulationEnums.REJECT.toString())){
                result.add(get);
            }
        }

        return result;
    }
    /*
    *决策统计 漏斗图
    * @param StatisticsSearchForm
     * @return
     */
    public List<DecisionStatisticsFunnelVO> funnel(StatisticsSearchForm statisticsSearchForm){
        String startDate = statisticsSearchForm.getStartDate();
        String endDate = statisticsSearchForm.getEndDate();
        String packageCode = statisticsSearchForm.getPackageCode();
        String status = PublishStatus.published.toString();
        String processCode = statisticsSearchForm.getProcessCode();
        List<DecisionStatisticsFunnelVO> list = decisionStatisticsMapper.funnel(startDate,endDate,packageCode,status,processCode);

        List<DecisionStatisticsFunnelVO> result = new ArrayList<>();

        for (DecisionStatisticsFunnelVO get : list){
            if (get.getResult().equals(DecisionSimulationEnums.REJECT.toString())){
                result.add(get);
            }
        }

        for(int i = 0 ; i < result.size(); i++){
            for(int j = 0 ; j < result.size(); j++){
                if (result.get(i).getOrdered().equals("1")){
                    result.get(i).setConversionRate(1.00);
                    break;
                }
                else if (String.valueOf(Integer.parseInt(result.get(i).getOrdered()) - 1).equals(result.get(j).getOrdered())){
                    result.get(i).setConversionRate(result.get(i).getRejectCustomers() / result.get(j).getRejectCustomers());
                    break;
                }
            }

        }

        return result;
    }
    /*
     *获取规则包名称--TODO
     * @return key = packageName value = packageCode
     */
    public List<KeyValueVO> getPackageList(){

        List<KeyValueVO> list = new ArrayList<>();

        List<RulePackageDO> packageList = rulePackageJpaDAO.findAllByFlag(CommonConstant.NORMAL_FLAG);

        if (null == packageList || packageList.size() == 0){
            return list;
        }

        for (RulePackageDO packageDO : packageList){
            boolean flag = true;
            for (int i = 0 ; i < list.size(); i++){
                if (list.get(i).getKey().equals(packageDO.getPackageName())){
                    flag = false;
                    break;
                }
            }
            if (flag){
                list.add(new KeyValueVO(packageDO.getPackageName(),packageDO.getPackageCode()));
            }
        }

        return list;
    }
    /*
    *获取流程代码
    * @return
     */
    public List<KeyValueVO> getProcessCode(){

        List<KeyValueVO> list = new ArrayList<>();

        List<RuleProcessDO> processDOList = ruleProcessJpaDAO.findAllByFlag(CommonConstant.NORMAL_FLAG);

        if (null == processDOList || processDOList.size() == 0){
            return list;
        }

        for (RuleProcessDO processDO : processDOList){
            boolean flag = true;
            for (int i = 0 ; i < list.size(); i++){
                if (list.get(i).getKey().equals(processDO.getProcessName())){
                    flag = false;
                    break;
                }
            }
            if (flag){
                list.add(new KeyValueVO(processDO.getProcessName(),processDO.getProcessCode()));
            }
        }

        return list;
    }

}