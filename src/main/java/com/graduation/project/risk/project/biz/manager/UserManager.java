package com.graduation.project.risk.project.biz.manager;

import com.graduation.project.risk.common.core.biz.MD5Util;
import com.graduation.project.risk.project.dal.jpa.dao.sys.SysUserJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysPermissionDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysUserDO;
import com.graduation.project.risk.project.dal.mybaits.dao.UserMapper;
import com.graduation.project.risk.project.web.form.user.UserAddForm;
import com.graduation.project.risk.project.web.vo.menu.MenuSecondVO;
import com.graduation.project.risk.project.web.vo.menu.MenuVO;
import com.graduation.project.risk.project.web.vo.user.GetUserListVO;
import com.graduation.project.risk.project.biz.enums.CommonPermission;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManager {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysUserJpaDAO sysUserJpaDAO;

    public void addUser(UserAddForm userAddForm) {
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setName(userAddForm.getName());
        sysUserDO.setAccount(userAddForm.getAccount());
        sysUserDO.setPassword(MD5Util.encode(userAddForm.getPassword()));
        sysUserDO.setStatus(CommonStatus.normal);
        sysUserDO.setRemark(userAddForm.getRemark());
        sysUserJpaDAO.save(sysUserDO);
    }

//    public SysUserDO getUserByAccount(String account) {
//        return userMapper.getUserByAccount(account);
//    }

    /**
     * @return com.huijie.xcode.risk.project.web.vo.user.GetUserListVO
     * @Description get user list for selected
     * @Author Santos
     * @Date 2019-04-08
     * @Param []
     **/
    public List<GetUserListVO> getUserList() {
        List<GetUserListVO> getUserListVOS = new ArrayList<>();
        List<SysUserDO> sysUserDOList = sysUserJpaDAO.findAll();
        if (sysUserDOList != null && sysUserDOList.size() > 0) {
            for (SysUserDO sysUserDO : sysUserDOList
            ) {
                GetUserListVO getUserListVO = new GetUserListVO();
                getUserListVO.setUserId(sysUserDO.getId());
                getUserListVO.setUserName(sysUserDO.getName());
                getUserListVOS.add(getUserListVO);
            }
        }
        return getUserListVOS;
    }

    /**
     * @return java.util.List<com.huijie.xcode.risk.project.web.vo.menu.MenuVO>
     * @Description get user's permission menu
     * @Author Santos
     * @Date 2019-04-11
     * @Param [id]
     **/
    public List<MenuVO> getUserMenu(Long id) {
        List<MenuVO> menuVOS = new ArrayList<>();
        //get user's all permission
        List<SysPermissionDO> sysPermissionDOS = userMapper.getPermissionByUserId(id);
        if (sysPermissionDOS != null && sysPermissionDOS.size() > 0) {
            //get user's all menu permission
            List<SysPermissionDO> sysPermissionDOSMenus = new ArrayList<>();
            for (SysPermissionDO sysPermissionDO : sysPermissionDOS
            ) {
                if (sysPermissionDO.getType() == CommonPermission.menu) {
                    sysPermissionDOSMenus.add(sysPermissionDO);
                }
            }
            if (sysPermissionDOSMenus != null && sysPermissionDOSMenus.size() > 0) {
                List<SysPermissionDO> firstSysDOs = new ArrayList<>();
                List<SysPermissionDO> secondSysDOs = new ArrayList<>();
                //get first menu list and second menu list
                for (SysPermissionDO sysPermissionDO : sysPermissionDOSMenus
                ) {
                    if (sysPermissionDO.getParentId().longValue() == 0) {
                        firstSysDOs.add(sysPermissionDO);
                    } else {
                        secondSysDOs.add(sysPermissionDO);
                    }
                }

                //according to first munu ,deal with every second menu
                for (SysPermissionDO sysPermissionDOfirst : firstSysDOs
                ) {
                    MenuVO menuVO = new MenuVO();
                    Long firstId = sysPermissionDOfirst.getId();
                    menuVO.setFirstMenuId(firstId);
                    menuVO.setFirstMenuName(sysPermissionDOfirst.getName());
                    List<MenuSecondVO> menuSecondVOS = new ArrayList<>();
                    for (SysPermissionDO sysPermissionDOsecond : secondSysDOs
                    ) {
                        if (sysPermissionDOsecond.getParentId().longValue() == firstId) {
                            MenuSecondVO menuSecondVO = new MenuSecondVO();
                            menuSecondVO.setSecondMenuId(sysPermissionDOsecond.getId());
                            menuSecondVO.setSecondMenuName(sysPermissionDOsecond.getName());
                            menuSecondVO.setSecondHref(sysPermissionDOsecond.getHref());
                            menuSecondVOS.add(menuSecondVO);
                        }
                    }
                    menuVO.setList(menuSecondVOS);
                    menuVOS.add(menuVO);
                }
                return menuVOS;
            }
            return menuVOS;
        }
        return menuVOS;
    }


}
