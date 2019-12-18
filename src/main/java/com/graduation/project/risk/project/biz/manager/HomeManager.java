package com.graduation.project.risk.project.biz.manager;


import com.graduation.project.risk.project.dal.jpa.dao.pp.PpRiskLoanInfoJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dao.pp.PpRiskLoanResultJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRiskLoanInfoDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRiskLoanResultDO;
import com.graduation.project.risk.project.dal.mybaits.dao.HomeStatisticsMapper;
import com.graduation.project.risk.project.dal.mybaits.dataobject.HomeStatisticsDO;
import com.graduation.project.risk.project.web.vo.home.HomeStatisticsVO;
import com.graduation.project.risk.project.web.vo.home.HomeTodayVO;
import com.graduation.project.risk.project.biz.enums.CommonRuleEnums;
import com.graduation.project.risk.project.web.form.home.HomeSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HomeManager {


    @Autowired
    private PpRiskLoanInfoJpaDAO ppRiskLoanInfoJpaDAO;

    @Autowired
    private PpRiskLoanResultJpaDAO ppRiskLoanResultJpaDAO;

    @Autowired
    private HomeStatisticsMapper statisticsMapper;

    public HomeTodayVO getTodayData(){

        HomeTodayVO homeTodayVO = new HomeTodayVO();
        homeTodayVO.setTotalNumber(0);
        homeTodayVO.setRejectEventNumber(0);
        homeTodayVO.setPassEventNumber(0);
        homeTodayVO.setPassRate(BigDecimal.ZERO);
        homeTodayVO.setRejectRate(BigDecimal.ZERO);
        homeTodayVO.setApplicationAmount(BigDecimal.ZERO);
        homeTodayVO.setAverageAmount(BigDecimal.ZERO);

        List<Date> timeRange = getTimeRange(-8);

        //今日事件总量
        Integer totalNumber = ppRiskLoanResultJpaDAO.countByCreateTimeBetween(timeRange.get(0), timeRange.get(1));

        if (totalNumber == 0 ){ return homeTodayVO;}
        homeTodayVO.setTotalNumber(totalNumber);

        //拒绝事件数量
        Integer rejectEventNumber = ppRiskLoanResultJpaDAO.findByCreateTimeBetweenAndResult(timeRange.get(0), timeRange.get(1), CommonRuleEnums.REJECT).size();
        if (rejectEventNumber != 0){
            //事件拒绝率
            BigDecimal rejectRate = new BigDecimal((float)rejectEventNumber / totalNumber).setScale(2, BigDecimal.ROUND_DOWN);
            homeTodayVO.setRejectRate(rejectRate);
            homeTodayVO.setRejectEventNumber(rejectEventNumber);
        }

        //通过事件
        List<PpRiskLoanResultDO> passEventList = ppRiskLoanResultJpaDAO.findByCreateTimeBetweenAndResult(timeRange.get(0), timeRange.get(1), CommonRuleEnums.PASS);

        if (passEventList != null && passEventList.size() > 0){

            List<String> passEventOrderCodeList = new ArrayList<>();
            for (PpRiskLoanResultDO ppRiskLoanResultDO : passEventList) {
                passEventOrderCodeList.add(ppRiskLoanResultDO.getOrderCode());
            }

            BigDecimal applicationAmount = BigDecimal.ZERO.setScale(2);
            BigDecimal averageAmount = BigDecimal.ZERO;

            List<PpRiskLoanInfoDO> passEventInfoList = ppRiskLoanInfoJpaDAO.findByOrderCodeIn(passEventOrderCodeList);
            //计算总的放款总额度
            for (PpRiskLoanInfoDO ppRiskLoanInfoDO : passEventInfoList) {
                BigDecimal addAmount = new BigDecimal(ppRiskLoanInfoDO.getApplicationAmount());
                applicationAmount = applicationAmount.add(addAmount);
            }

            //计算件均额度
            averageAmount = applicationAmount.divide(new BigDecimal(passEventList.size()),2, BigDecimal.ROUND_DOWN);

            //通过事件数
            Integer passEventNumber = passEventList.size();

            //事件通过率
            BigDecimal passRate = new BigDecimal((float) passEventNumber / totalNumber).setScale(2, BigDecimal.ROUND_DOWN);

            homeTodayVO.setPassEventNumber(passEventNumber);
            homeTodayVO.setPassRate(passRate);
            homeTodayVO.setApplicationAmount(applicationAmount);
            homeTodayVO.setAverageAmount(averageAmount);

        }

        return homeTodayVO;
    }

    public HomeStatisticsVO getStatisticsData(HomeSearchForm homeSearchForm){

        HomeStatisticsVO homeStatisticsVO = new HomeStatisticsVO();
        String startDate= homeSearchForm.getStartDate();
        String stopDate = homeSearchForm.getStopDate();
        String personProvince = homeSearchForm.getPersonProvince();

        List<HomeStatisticsDO> statisticsDOList= statisticsMapper.getStatisticsInfo(startDate, stopDate, personProvince);

        Integer totalNumber = 0;
        Integer passEventNumber = 0;
        Integer rejectEventNumber = 0;
        Integer reviewEventNumber = 0;

        Double passRate = 0.00;
        Double rejectRate = 0.00;
        Double reviewRate = 0.00;

        if (statisticsDOList != null && statisticsDOList.size() > 0){
            for (HomeStatisticsDO statisticsDO : statisticsDOList) {
                totalNumber += statisticsDO.getTotalNumber();
                passEventNumber += statisticsDO.getPassEventNumber();
                rejectEventNumber += statisticsDO.getRejectEventNumber();
                reviewEventNumber += statisticsDO.getReviewEventNumber();
            }
            passRate =  new BigDecimal(new Double(passEventNumber * 1.0 / totalNumber).toString()).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            rejectRate = new BigDecimal(new Double(rejectEventNumber * 1.0 / totalNumber).toString()).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            reviewRate = new BigDecimal(new Double(reviewEventNumber * 1.0 / totalNumber).toString()).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();


        }

        homeStatisticsVO.setTotalNumber(totalNumber);
        homeStatisticsVO.setPassEventNumber(passEventNumber);
        homeStatisticsVO.setRejectEventNumber(rejectEventNumber);
        homeStatisticsVO.setReviewEventNumber(reviewEventNumber);
        homeStatisticsVO.setPassRate(passRate);
        homeStatisticsVO.setRejectRate(rejectRate);
        homeStatisticsVO.setReviewRate(reviewRate);
        homeStatisticsVO.setStatisticsList(statisticsDOList);

        return homeStatisticsVO;
    }
    private static List<Date> getTimeRange(int dayOffset)  {
        List<Date> dateList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, dayOffset);
        String today = dateFormat.format(calendar.getTime());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try{
            dateList.add(dateFormat.parse(today + " 00:00:00"));
            dateList.add(dateFormat.parse(today + " 23:59:59"));
        }catch (ParseException e){
            e.printStackTrace();
        }

        return dateList;
    }



}
