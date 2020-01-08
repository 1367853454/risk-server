package com.graduation.project.risk.project.biz.manager;

import com.github.pagehelper.PageHelper;
import com.graduation.project.risk.common.core.biz.BizCoreException;
import com.graduation.project.risk.common.core.biz.StringUtil;
import com.graduation.project.risk.project.dal.jpa.dao.blacklist.*;
import com.graduation.project.risk.project.dal.jpa.dataobject.blacklist.*;
import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysUserDO;
import com.graduation.project.risk.project.dal.mybaits.dao.BlacklistMapper;
import com.graduation.project.risk.project.dal.mybaits.dao.BlacklistRecordMapper;
import com.graduation.project.risk.project.dal.mybaits.dataobject.BlackListRecordSearchDO;
import com.graduation.project.risk.project.dal.mybaits.dataobject.BlackListSearchDO;
import com.graduation.project.risk.project.web.form.blacklist.AdduserIntoBlacklistForm;
import com.graduation.project.risk.project.web.form.blacklist.BlacklistRecordForm;
import com.graduation.project.risk.project.web.form.blacklist.BlacklistRemoveForm;
import com.graduation.project.risk.project.web.form.blacklist.BlacklistSearchForm;
import com.graduation.project.risk.project.web.vo.blacklist.BlacklistSearchPhoneNumberVO;
import com.graduation.project.risk.project.web.vo.blacklist.BlacklistSearchReasonVO;
import com.graduation.project.risk.project.web.vo.blacklist.BlacklistSearchVO;
import com.graduation.project.risk.common.core.biz.ErrorCode;
import com.graduation.project.risk.common.core.dal.mongdb.query.PageUtil;
import com.graduation.project.risk.common.model.Page;
import com.graduation.project.risk.project.biz.enums.CommonInd;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import com.graduation.project.risk.project.biz.enums.CommonType;
import com.graduation.project.risk.project.biz.enums.OperatorType;
import com.graduation.project.risk.project.dal.jpa.dao.sys.SysUserJpaDAO;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

@Service
public class BlacklistManager {

    private Logger logger = LoggerFactory.getLogger(BlacklistManager.class);

    @Autowired
    private BlackListIdCardJpaDAO blackListIdCardJpaDAO;

    @Autowired
    private BlackListMobileJpaDAO blackListMobileJpaDAO;

    @Autowired
    private BlackListMobileReasonRelJpaDAO blackListMobileReasonRelJpaDAO;

    @Autowired
    private BlackListIdCardReasonRelJpaDAO blackListIdCardReasonRelJpaDAO;

    @Autowired
    private BlackListRecordJpaDAO blackListRecordJpaDAO;

    @Autowired
    private BlackListSourceReasonJpaDAO blackListSourceReasonJpaDAO;


    @Autowired
    private BlacklistRecordMapper blacklistRecordMapper;

    @Autowired
    private BlacklistMapper addUserIntoBlacklistMapper;

    @Autowired
    private BlacklistMapper blacklistMapper;

    @Autowired
    private SysUserJpaDAO sysUserJpaDAO;

    public void add(AdduserIntoBlacklistForm adduserIntoBlacklistForm) {

        Long blacklistSourceReasonId = adduserIntoBlacklistForm.getBlacklistSourceReasonId();

        List<Long> sourceReasonIds = new ArrayList<>();
        List<BlackListSourceReasonDO> blackListSourceReasonDOS = blackListSourceReasonJpaDAO.findAllByBlacklistSourceId(4L);
        for (BlackListSourceReasonDO blackListSourceReasonDO : blackListSourceReasonDOS){
            sourceReasonIds.add(blackListSourceReasonDO.getId());
        }
        if(!sourceReasonIds.contains(blacklistSourceReasonId)){
            throw new BizCoreException(ErrorCode.NOT_INTERNAL_BLACKLIST_ID);
        }

        boolean idcardFlag = false;
        //boolean mobileFlag = false;
        //boolean mobileFlag_ = true;
        BlackListIdCardDO card = blackListIdCardJpaDAO.findByIdcardAndStatus(adduserIntoBlacklistForm.getIdcard(), CommonStatus.normal);

        //add by Santos.idcard existed,if idcard's name != form's name,return false
        if (card != null){
            if (!adduserIntoBlacklistForm.getName().toLowerCase().equals(card.getName().toLowerCase())){
                throw new BizCoreException(ErrorCode.EXISTING_IDCARD_NAME_NOT_MATCH_INCOMING_IDCARD_NAME);
            }
        }

        BlackListMobileDO phone = blackListMobileJpaDAO.findByMobileAndStatus(adduserIntoBlacklistForm.getMobile(),CommonStatus.normal);

        if (phone != null){
            throw new BizCoreException(ErrorCode.MOBILE_EXISTED);
        }

        else {

            BlackListIdCardDO blackListIdCardDO = new BlackListIdCardDO();
            BlackListMobileDO blackListMobileDO = new BlackListMobileDO();
            BlackListIdCardReasonRelDO blackListIdCardReasonRelDO = new BlackListIdCardReasonRelDO();
            BlackListRecordDO blackListRecordDO = new BlackListRecordDO();

            if (card == null)
            {
                blackListIdCardDO.setIdcard(adduserIntoBlacklistForm.getIdcard());
                blackListIdCardDO.setName(adduserIntoBlacklistForm.getName());
                blackListIdCardDO.setHjBlacklistInd(CommonInd.yes);
                blackListIdCardDO.setHjBlacklistTime(new Date());
                blackListIdCardDO.setType(CommonType.black);
                blackListIdCardDO.setStatus(CommonStatus.normal);

                blackListIdCardJpaDAO.save(blackListIdCardDO);
                idcardFlag = true;
                card = blackListIdCardJpaDAO.findByIdcardAndStatus(adduserIntoBlacklistForm.getIdcard(),CommonStatus.normal);
            }

            blackListMobileDO.setMobile(adduserIntoBlacklistForm.getMobile());
            blackListMobileDO.setName(card.getName());
            blackListMobileDO.setHjBlacklistInd(CommonInd.yes);
            blackListMobileDO.setHjBlacklistTime(new Date());
            blackListMobileDO.setOperatorId(((SysUserDO) SecurityUtils.getSubject().getPrincipal()).getId());
            blackListMobileDO.setType(CommonType.black);
            blackListMobileDO.setStatus(CommonStatus.normal);
            blackListMobileDO.setBlacklistIdcardId(card.getId());

            blackListMobileJpaDAO.save(blackListMobileDO);

            if(idcardFlag)
            {
                blackListIdCardReasonRelDO.setBlacklistIdcardId(card.getId());
                blackListIdCardReasonRelDO.setBlacklistSourceId(4L);//hj_blacklist
                blackListIdCardReasonRelDO.setBlacklistSourceReasonId(adduserIntoBlacklistForm.getBlacklistSourceReasonId());//reason
                blackListIdCardReasonRelJpaDAO.save(blackListIdCardReasonRelDO);
            }

            phone = blackListMobileJpaDAO.findByMobileAndStatus(adduserIntoBlacklistForm.getMobile(),CommonStatus.normal);
            BlackListMobileReasonRelDO blackListMobileReasonRelDO = new BlackListMobileReasonRelDO();
            blackListMobileReasonRelDO.setBlacklistMobileId(phone.getId());
            blackListMobileReasonRelDO.setBlacklistSourceId(4L);
            blackListMobileReasonRelDO.setBlacklistSourceReasonId(adduserIntoBlacklistForm.getBlacklistSourceReasonId());
            blackListMobileReasonRelJpaDAO.save(blackListMobileReasonRelDO);

            BlackListSourceReasonDO name = blackListSourceReasonJpaDAO.findById(adduserIntoBlacklistForm.getBlacklistSourceReasonId());

            blackListRecordDO.setName(card.getName());
            blackListRecordDO.setIdNumber(adduserIntoBlacklistForm.getIdcard());
            blackListRecordDO.setPhoneNumber(adduserIntoBlacklistForm.getMobile());
            blackListRecordDO.setOperatorId(((SysUserDO) SecurityUtils.getSubject().getPrincipal()).getId());
            blackListRecordDO.setOperatorTime(new Date());
            blackListRecordDO.setOperatorType(OperatorType.entry);
            blackListRecordDO.setReason(name.getName());
            blackListRecordDO.setType(CommonType.black);

            blackListRecordJpaDAO.save(blackListRecordDO);

        }

    }


    /**
     * @Description blacklist record page
     * @Author Santos
     * @Date 2019-04-04
     * @Param [blacklistRecordForm]
     * @return com.graduation.project.risk.common.model.Page
     **/
    public Page getBlacklistRecord(BlacklistRecordForm blacklistRecordForm) {
        PageHelper.startPage(blacklistRecordForm.getPageNumber() , blacklistRecordForm.getPageSize());
        String operator = blacklistRecordForm.getOperator();
        if (!StringUtil.isEmpty(operator)){
            SysUserDO sysUserDO = sysUserJpaDAO.findByAccount(operator);
            if (sysUserDO!=null){
                blacklistRecordForm.setOperatorId(sysUserDO.getId().toString());
            }else{
                blacklistRecordForm.setOperatorId("-1");
            }
        }
        List<BlackListRecordSearchDO> list = blacklistRecordMapper.getBlacklistRecordPage(blacklistRecordForm);
        Page<BlackListRecordSearchDO> page = PageUtil.toPage(list);
        return page;
    }

    /**
     * @Description search for blacklist
     * @Author Santos
     * @Date 2019-04-05
     * @Param [blacklistSearchForm]
     * @return
     **/
    public Set<BlacklistSearchVO> blacklistSearch(BlacklistSearchForm blacklistSearchForm) {
        Set<BlacklistSearchVO> blacklistSearchVOS = new HashSet<>();
        String phoneNumber = blacklistSearchForm.getPhoneNumber();
        String idNumber = blacklistSearchForm.getIdNumber();

        if (StringUtil.isNotEmpty(idNumber)) {
            List<BlackListSearchDO> blackListSearchDOS = blacklistMapper.getBlackListSearchDOByIdNumber(idNumber, CommonStatus.normal, CommonType.black);
            if (blackListSearchDOS != null && blackListSearchDOS.size() > 0) {
                List<BlacklistSearchPhoneNumberVO> phoneNumbers = new ArrayList<>();
                for (BlackListSearchDO blackListSearchDO : blackListSearchDOS
                ) {
                    BlacklistSearchPhoneNumberVO blacklistSearchPhoneNumberVO = new BlacklistSearchPhoneNumberVO();
                    blacklistSearchPhoneNumberVO.setBlacklistMobileId(blackListSearchDO.getMobileId());
                    blacklistSearchPhoneNumberVO.setMobile(blackListSearchDO.getMobile());
                    phoneNumbers.add(blacklistSearchPhoneNumberVO);
                }
                BlackListSearchDO blackListSearchDO = blackListSearchDOS.get(0);
                BlacklistSearchVO blacklistSearchVO1 = new BlacklistSearchVO();
                blacklistSearchVO1.setApplicationTime(blackListSearchDO.getApplicationTime());
                blacklistSearchVO1.setBlacklistIdcardId(blackListSearchDO.getBlacklistIdcardId());
                blacklistSearchVO1.setIdNumber(blackListSearchDO.getIdNumber());
                blacklistSearchVO1.setName(blackListSearchDO.getName());
                blacklistSearchVO1.setPhoneNumbers(phoneNumbers);
                blacklistSearchVO1.setState(blackListSearchDO.getStatus());
                blacklistSearchVOS.add(blacklistSearchVO1);
            }
        }

        if (StringUtil.isNotEmpty(phoneNumber)) {
            List<BlackListSearchDO> blackListSearchDOS = blacklistMapper.getBlackListSearchDOByPhoneNumber(phoneNumber, CommonStatus.normal, CommonType.black);
            if (blackListSearchDOS != null && blackListSearchDOS.size() > 0) {
                List<BlacklistSearchPhoneNumberVO> phoneNumbers = new ArrayList<>();
                for (BlackListSearchDO blackListSearchDO : blackListSearchDOS
                ) {
                    BlacklistSearchPhoneNumberVO blacklistSearchPhoneNumberVO = new BlacklistSearchPhoneNumberVO();
                    blacklistSearchPhoneNumberVO.setBlacklistMobileId(blackListSearchDO.getMobileId());
                    blacklistSearchPhoneNumberVO.setMobile(blackListSearchDO.getMobile());
                    phoneNumbers.add(blacklistSearchPhoneNumberVO);
                }
                BlackListSearchDO blackListSearchDO = blackListSearchDOS.get(0);
                BlacklistSearchVO blacklistSearchVO2 = new BlacklistSearchVO();
                blacklistSearchVO2.setApplicationTime(blackListSearchDO.getApplicationTime());
                blacklistSearchVO2.setBlacklistIdcardId(blackListSearchDO.getBlacklistIdcardId());
                blacklistSearchVO2.setIdNumber(blackListSearchDO.getIdNumber());
                blacklistSearchVO2.setName(blackListSearchDO.getName());
                blacklistSearchVO2.setPhoneNumbers(phoneNumbers);
                blacklistSearchVO2.setState(blackListSearchDO.getStatus());
                blacklistSearchVOS.add(blacklistSearchVO2);
            }
        }
        return blacklistSearchVOS;
    }


    /**
     * @Description remove blacklist
     * @Author Santos
     * @Date 2019-04-08
     * @Param [blacklistRemoveForm]
     * @return boolean
     **/
    public boolean blacklistRemove(BlacklistRemoveForm blacklistRemoveForm) {
        String blacklistMobile = blacklistRemoveForm.getBlacklistMobile();
        Long blacklistIdcardId = blacklistRemoveForm.getBlacklistIdcardId();
        String removeReason = blacklistRemoveForm.getRemoveReason();

        List<String> ids = new ArrayList<>();
        List<BlackListMobileDO> blackListMobileDOS = blacklistMapper.getBlackListMobileDOByBlacklistIdcardId(blacklistIdcardId);
        BlackListMobileDO blackListMobileDO = new BlackListMobileDO();
        if (blackListMobileDOS!=null && blackListMobileDOS.size()>0){
            for (BlackListMobileDO blackListMobileDOF:blackListMobileDOS
            ) {
                if (blackListMobileDOF.getMobile().equals(blacklistMobile)){
                    blackListMobileDO = blackListMobileDOF;
                }
            }
        }

        if (blackListMobileDO == null){
            throw new BizCoreException(ErrorCode.MISSING_PARAMETER);
        }

        BlackListIdCardDO blackListIdCardDO = blackListIdCardJpaDAO.findOne(blacklistIdcardId);
//        BlackListMobileDO blackListMobileDO = blackListMobileJpaDAO.findOne(blacklistMobile);
        if (blackListMobileDOS != null && blackListMobileDOS.size()>0){
            //remove mobile
            blackListMobileDO.setStatus(CommonStatus.deleted);
            blackListMobileJpaDAO.save(blackListMobileDO);

            if (blackListMobileDOS.size()==1){
                //remove idcard and mobile
                blackListIdCardDO.setStatus(CommonStatus.deleted);
                blackListIdCardJpaDAO.save(blackListIdCardDO);
            }

            BlackListRecordDO blackListRecordDO = new BlackListRecordDO();
            blackListRecordDO.setName(blackListIdCardDO.getName());
            blackListRecordDO.setIdNumber(blackListIdCardDO.getIdcard());
            blackListRecordDO.setPhoneNumber(blackListMobileDO.getMobile());
            blackListRecordDO.setOperatorId(((SysUserDO) SecurityUtils.getSubject().getPrincipal()).getId());
            blackListRecordDO.setOperatorTime(new Date());
            blackListRecordDO.setOperatorType(OperatorType.remove);
            blackListRecordDO.setReason(removeReason);
            blackListRecordDO.setType(CommonType.black);
            blackListRecordJpaDAO.save(blackListRecordDO);
        }else{
            logger.error("blacklistRemove.getBlackListMobileDOByBlacklistIdcardId result is null");
            throw new BizCoreException(ErrorCode.NOT_EXIST_BY_PARAMS);
        }

        return true;
    }

    public List<BlacklistSearchReasonVO> getReasonIdList(Long SourceId){
        List<BlacklistSearchReasonVO> getBlacklistSearchReasonVOS = new ArrayList<>();
        List<BlackListSourceReasonDO> blackListSourceReasonDOList = blackListSourceReasonJpaDAO.findAllByBlacklistSourceId(SourceId);//只需要内部部分
        if( blackListSourceReasonDOList != null && blackListSourceReasonDOList.size() > 0){
            for (BlackListSourceReasonDO blackListSourceReasonDO : blackListSourceReasonDOList){
                BlacklistSearchReasonVO getBlacklistSearchReasonVO = new BlacklistSearchReasonVO();
                getBlacklistSearchReasonVO.setBlacklistSourceReasonId(blackListSourceReasonDO.getId());
                getBlacklistSearchReasonVO.setName(blackListSourceReasonDO.getName());
                getBlacklistSearchReasonVOS.add(getBlacklistSearchReasonVO);

            }
        }
        return getBlacklistSearchReasonVOS;
    }
}
