package com.graduation.project.risk.project.biz.manager;

import com.github.pagehelper.PageHelper;
import com.graduation.project.risk.common.core.biz.*;
import com.graduation.project.risk.project.dal.jpa.dao.sys.*;
import com.graduation.project.risk.project.dal.jpa.dataobject.sys.*;
import com.graduation.project.risk.project.dal.mybaits.dao.SysRolePermissionMapper;
import com.graduation.project.risk.project.dal.mybaits.dao.SysUserMapper;
import com.graduation.project.risk.project.dal.mybaits.dao.SysUserRoleMapper;
import com.graduation.project.risk.project.web.form.system.*;
import com.graduation.project.risk.project.web.vo.system.*;
import com.graduation.project.risk.common.core.dal.mongdb.query.PageUtil;
import com.graduation.project.risk.common.model.Page;
import com.graduation.project.risk.project.biz.constants.CommonConstant;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.*;

@Service
public class SystemManager {

    private Logger logger = LoggerFactory.getLogger(SystemManager.class);

    @Autowired
    private SysRoleJpaDAO sysRoleJpaDAO;

    @Autowired
    private SysUserJpaDAO sysUserJpaDAO;

    @Autowired
    private SysPermissionJpaDAO sysPermissionJpaDAO;

    @Autowired
    private SysUserRoleJpaDAO sysUserRoleJpaDAO;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysRolePermissionJpaDAO sysRolePermissionJpaDAO;

    /*
     *增加角色
     *
     * @param AddRoleForm
     *
     */
    public void addRole(AddRoleForm addRoleForm){

        SysRoleDO role  = sysRoleJpaDAO.findByName(addRoleForm.getName());
        if (null != role){
            throw new BizCoreException(ErrorCode.ROLE_NAME_EXISTED);
        }

        SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setName(addRoleForm.getName());
        if (StringUtil.isNotEmpty(addRoleForm.getDescription())){
            sysRoleDO.setDescription(addRoleForm.getDescription());
        }

        sysRoleJpaDAO.save(sysRoleDO);

    }
    /*
     *修改密码
     *
     * @param ChangePasswordForm
     */
    public void changePassword(ChangePasswordForm changePasswordForm){

         SysUserDO User = sysUserJpaDAO.findByAccountAndStatus(changePasswordForm.getAccount(),CommonStatus.normal);

         if (User == null){
             throw new BizCoreException(ErrorCode.USER_NOT_EXISTED);
         }

        String oldPassword = MD5Util.encode(changePasswordForm.getOlePassword());

        boolean pwd = oldPassword.equals(User.getPassword());

        if (!pwd){
            throw new BizCoreException(ErrorCode.PASSWORD_ERROR);
        }

        User.setPassword(MD5Util.encode(changePasswordForm.getNewPassword()));
        User.setUpdateTime(new Date());
        sysUserJpaDAO.save(User);

    }
    /*
     *左侧菜单栏
     * 应该没用的
     * @param MenuForm
     */
    public void roleMenuAdd(MenuForm menuForm){

        if (menuForm.getParent_id() < 0){
            throw new BizCoreException(ErrorCode.PARENT_ID_LESS_THAN_ZERO);
        }

        List<String> permission = new ArrayList<>();
        List<SysPermissionDO> sysPermissionDOS = sysPermissionJpaDAO.findAll();
        for (SysPermissionDO sysPermissionDO : sysPermissionDOS){
            permission.add(sysPermissionDO.getPermission());
        }
        if (permission.contains(menuForm.getPermission())){
            throw new BizCoreException(ErrorCode.PERMISSION_REPETITION);
        }

        SysPermissionDO sysPermissionDO = new SysPermissionDO();
        sysPermissionDO.setParentId(menuForm.getParent_id());
        sysPermissionDO.setName(menuForm.getName());
        sysPermissionDO.setHref(menuForm.getHref());
        sysPermissionDO.setType(menuForm.getType());
        sysPermissionDO.setPermission(menuForm.getPermission());
        sysPermissionDO.setCreateTime(new Date());
        sysPermissionDO.setUpdateTime(new Date());

        sysPermissionJpaDAO.save(sysPermissionDO);

    }
    /*
     *重置密码
     * @param ResetForm
     */
    public void resetPassword(ResetForm resetForm){
        SysUserDO User = sysUserJpaDAO.findByAccountAndStatus(resetForm.getAccount(),CommonStatus.normal);

        if (User == null){
            throw new BizCoreException(ErrorCode.USER_NOT_EXISTED);
        }

        User.setPassword("e10adc3949ba59abbe56e057f20f883e");
        sysUserJpaDAO.save(User);

    }
    /*
     *状态设置为启用
     * @param PackageStartAndStopForm
     */
    public void startUsing(StartAndStopForm startAndStopForm){

        SysUserDO User = sysUserJpaDAO.findByAccountAndStatus(startAndStopForm.getAccount(), CommonStatus.stop);

        if (null == User){
            throw new BizCoreException(ErrorCode.USER_NOT_EXISTED_BY_THIS_STATUS);
        }

        User.setStatus(CommonStatus.normal);
        sysUserJpaDAO.save(User);

    }
    /*
     *状态设置为停用
     * @param PackageStartAndStopForm
     */
    public void stopUsing(StartAndStopForm startAndStopForm){

        SysUserDO User = sysUserJpaDAO.findByAccountAndStatus(startAndStopForm.getAccount(), CommonStatus.normal);

        if (null == User){
            throw new BizCoreException(ErrorCode.USER_NOT_EXISTED_BY_THIS_STATUS);
        }

        User.setStatus(CommonStatus.stop);
        sysUserJpaDAO.save(User);

    }
    /*
     *增加用户
     * @param AddUserForm
     * @return
     */
    public void addUser(AddUserForm addUserForm){
        //表里面设置了唯一索引 状态+用户名
        SysUserDO add = sysUserJpaDAO.findByAccountAndFlag(addUserForm.getAccount(),CommonConstant.NORMAL_FLAG);

        if (null != add ){
            throw new BizCoreException(ErrorCode.USER_EXISTED);
        }

        SysUserDO sysUserDO = new SysUserDO(addUserForm);
        sysUserJpaDAO.save(sysUserDO);

        add = sysUserJpaDAO.findByAccountAndStatus(addUserForm.getAccount(),CommonStatus.normal);

        List<Long> roleId = addUserForm.getRole();
        if (null != roleId){
            for(Long i : roleId){
                SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
                sysUserRoleDO.setUserId(add.getId());
                sysUserRoleDO.setRoleId(i);

                sysUserRoleJpaDAO.save(sysUserRoleDO);
            }
        }

    }
    /*
    *获取角色id
    *
    * @return
     */
    public List<RoleVO> getRoleId(){
        List<RoleVO> getRoleListVO = new ArrayList<>();
        List<SysRoleDO> getRoleListVOS = sysRoleJpaDAO.findAll();
        for (SysRoleDO sysRoleDO : getRoleListVOS){
            RoleVO roleVO = new RoleVO();
            roleVO.setRoleId(sysRoleDO.getId());
            roleVO.setName(sysRoleDO.getName());
            getRoleListVO.add(roleVO);
        }

        return getRoleListVO;
    }
    /*
     *查看用户信息
     * @param PackageStartAndStopForm : account
     * @return
     */
    public GetUserInfoVO userCheckAndEdit(UserInfoForm userInfoForm){

        SysUserDO User = sysUserJpaDAO.findByAccountAndFlag(userInfoForm.getAccount(),CommonConstant.NORMAL_FLAG);

        if (null == User){
            throw new BizCoreException(ErrorCode.USER_NOT_EXISTED);
        }

        GetUserInfoVO getUserInfoVO = new GetUserInfoVO();
        getUserInfoVO.setAccount(User.getAccount());
        getUserInfoVO.setName(User.getName());
        getUserInfoVO.setMobile(User.getMobile());

        //Mybatis
        getUserInfoVO.setRole(sysUserRoleMapper.getSysRole(User.getId()));

        return getUserInfoVO;
    }
    /*
    *编辑用户信息
    *
    * @param EditUserForm
    * @return
     */
    public void editUser(EditUserForm editUserForm){
        String oldAccount = editUserForm.getOldAccount();
        String newAccount = editUserForm.getNewAccount();
        SysUserDO edit = new SysUserDO();
        if (!oldAccount.equals(newAccount)){
            edit = sysUserJpaDAO.findByAccountAndFlag(editUserForm.getNewAccount(),CommonConstant.NORMAL_FLAG);
            if (null != edit){
                throw new BizCoreException(ErrorCode.USER_EXISTED);
            }
        }

        edit = sysUserJpaDAO.findByAccountAndFlag(editUserForm.getOldAccount(),CommonConstant.NORMAL_FLAG);
        if (null == edit){
            throw new BizCoreException(ErrorCode.USER_NOT_EXISTED);
        }

        edit.setAccount(editUserForm.getNewAccount());
        if (!CommonUtil.isEmpty(editUserForm.getMobile())){
            edit.setMobile(editUserForm.getMobile());
        }
        if (StringUtil.isNotEmpty(editUserForm.getName())){
            edit.setName(editUserForm.getName());
        }
        edit.setUpdateTime(new Date());
        sysUserJpaDAO.save(edit);

        //编辑的部分 如1、3、5，6
        List<Long> roleId = editUserForm.getRole();
        if (roleId != null){
            //系统内已有的部分如1、2、4、5
            List<SysUserRoleDO> SysId = sysUserRoleJpaDAO.findAllByUserId(edit.getId());
            List<Long> sysId = new ArrayList<>();
            for (SysUserRoleDO id : SysId){
                sysId.add(id.getRoleId());
            }
            //最终应该更新1，5  增加3，6  删除 2，4
            Collection exists = new ArrayList(sysId); //不同部分
            Collection notexists = new ArrayList(sysId); //相同部分

            exists.removeAll(roleId); //这个部分是要删除的
            notexists.removeAll(exists); //这个部分是要更新的

            Collection exists1 = new ArrayList(roleId);

            exists1.removeAll(sysId);//这个部分是要增加的

            for (int i = 0 ; i < notexists.size(); i ++){ //更新部分
                Date updateTime = new Date();
                Long roleID = (Long) ((ArrayList) notexists).get(i);
                sysUserRoleJpaDAO.updateUpdatetime(updateTime,edit.getId(), roleID);
            }
            for (int i = 0 ; i < exists.size(); i++){ //删除部分
                Long roleID = (Long) ((ArrayList) exists).get(i);
                sysUserRoleJpaDAO.deleteByUserIdAndRoleId(edit.getId(),roleID);
            }
            for (int i = 0 ; i < exists1.size(); i++){ //增加部分
                Long roleID = (Long) ((ArrayList) exists1).get(i);
                SysUserRoleDO add = new SysUserRoleDO();
                add.setUserId(edit.getId());
                add.setRoleId(roleID);
                sysUserRoleJpaDAO.save(add);
            }
        }

    }
    /*
    *用户查询
    * @param SysUserSearchForm
    * @return
     */
    public Page<SysUserSearchVO> sysUserSearch(SysUserSearchForm sysUserSearchForm){
        PageHelper.startPage(sysUserSearchForm.getPageNumber(),sysUserSearchForm.getPageSize());
        String name = sysUserSearchForm.getName();
        String status = sysUserSearchForm.getStatus();

        if(StringUtil.isEmpty(status) && StringUtil.isEmpty(name)){
            throw new BizCoreException(ErrorCode.PARAM_MISS);
        }

        List<SysUserSearchVO> sysUserSearchVOS = sysUserMapper.userSearch(name,status);
        Page<SysUserSearchVO> page = PageUtil.toPage(sysUserSearchVOS);

        return page;

    }
    /*
    *角色查询
    * @param SysRoleForm
    * @return
     */
    public SysRoleSearchVO sysRoleSearch(SysRoleForm sysRoleFormd){
        String name = sysRoleFormd.getName();

        SysRoleDO sysRoleDO = sysRoleJpaDAO.findByName(name);

        if (null == sysRoleDO){
            throw new BizCoreException(ErrorCode.ROLE_NAME_NOT_EXISTED);
        }

        SysRoleSearchVO sysRoleSearchVO = new SysRoleSearchVO();
        sysRoleSearchVO.setRoleId(sysRoleDO.getId());
        sysRoleSearchVO.setDescription(sysRoleDO.getDescription());
        sysRoleSearchVO.setName(sysRoleDO.getName());

        return sysRoleSearchVO;

    }
    /*
     *一个角色所有的全部信息
     *
     * @param SysRolePermissionForm
     * @return
     */
    public Set<SysRolePermissionForOneVO> rolePermission(SysRolePermissionForm sysRolePermissionForm){
        Long id = sysRolePermissionForm.getRoleId();

        Set<SysRole> sysRolePermissionVOS =  sysRolePermissionMapper.getPermission(id);
        Set<SysRolePermissionForOneVO> list3 = new HashSet<>();

        for (SysRole i : sysRolePermissionVOS){
            if (i.getParentId() == 0){
                for (SysRole j : sysRolePermissionVOS){
                    if (j.getParentId() == i.getId()){
                        for (SysRole k : sysRolePermissionVOS){
                            if (k.getParentId() == j.getId()){
                                SysRolePermissionForOneVO result3 = new SysRolePermissionForOneVO();
                                result3.setId(k.getId());
                                list3.add(result3);
                            }
                        }
                    }
                }
            }
        }

        return list3;
    }
    /*
     *所有的权限 排列好的顺序
     * @return
     *保证了每一个根节点后都跟着一个子节点，不管是否为空
     */
    public List<List<SysRolePermissionForResultVO>> permissionTree(){
        final List<SysPermissionDO> sysRolePermissionList = sysPermissionJpaDAO.findAll(); //数据

        List<List<SysRolePermissionForResultVO>> results = new ArrayList<>();
        List<SysRolePermissionVO> list3 = new ArrayList<>();
        List<SysRolePermissionForSecondVO> list2 = new ArrayList<>();
        List<SysRolePermissionForResultVO> list1 = new ArrayList<>();
        boolean flag_1 = false;
        boolean flag_2 = false;
        boolean flag_3 = false;

        for (SysPermissionDO i : sysRolePermissionList){
            if (i.getParentId() == 0){
                SysRolePermissionForResultVO result1 = new SysRolePermissionForResultVO();
                result1.setLabel(i.getPermission());
                result1.setId(i.getId());
                for (SysPermissionDO k : sysRolePermissionList){
                    if (k.getParentId() == i.getId()){
                        SysRolePermissionForSecondVO result2 = new SysRolePermissionForSecondVO();
                        result2.setLabel(k.getPermission());
                        result2.setId(k.getId());
                        for (SysPermissionDO j : sysRolePermissionList){
                            if (j.getParentId() == k.getId()){
                                SysRolePermissionVO result3 = new SysRolePermissionVO();
                                result3.setLabel(j.getPermission());
                                result3.setId(j.getId());
                                list3.add(result3);//第三层的节点
                                flag_3 = true;
                            }
                        }
                        if (flag_3){
                            result2.setChildren(list3);//第二层set第三层的东西
                            list3 = new ArrayList<>();
                            flag_3 = false;
                        }
                        list2.add(result2);//第二层的节点,将在第一层set
                        flag_2 = true;
                    }
                }
                if (flag_2){
                    result1.setChildren(list2);
                    list2 = new ArrayList<>();
                    flag_2 = false;
                }
                list1.add(result1);
                flag_1 = true;
            }
            if (flag_1) {
                results.add(list1);
                flag_1 = false;
            }
        }

        return results;
    }
    /*
     *授权提交
     * @param EditRolePermissiomForm
     */
    public void editRolePermission(EditRolePermissiomForm editRolePermissiomForm){
        SysRoleDO role = sysRoleJpaDAO.findById(editRolePermissiomForm.getRoleId());
        if (null == role){
            throw new BizCoreException(ErrorCode.ROLE_NAME_NOT_EXISTED);
        }
        //编辑的部分
        List<Long> permissionId = editRolePermissiomForm.getPermissionId();
        //系统内有的部分
        List<SysRolePermissionDO> SysPermission = sysRolePermissionJpaDAO.findAllByRoleId(editRolePermissiomForm.getRoleId());
        List<Long> sysPermission = new ArrayList<>();
        for(SysRolePermissionDO permission : SysPermission){
            sysPermission.add(permission.getPermissionId());
        }
        Collection exists = new ArrayList(sysPermission);//不同部分
        Collection notexists = new ArrayList(sysPermission);//相同部分

        exists.removeAll(permissionId); //删除的部分
        notexists.removeAll(exists);//更新的部分

        Collection exists1 = new ArrayList(permissionId);

        exists1.removeAll(sysPermission);//增加的部分

        for(int i = 0 ; i < notexists.size(); i++){ //更新部分
            Date update = new Date();
            Long permissionID = (Long) ((ArrayList) notexists).get(i);
            sysRolePermissionJpaDAO.updateUpdatetime(update,editRolePermissiomForm.getRoleId(),permissionID);
        }
        for (int i = 0 ; i < exists.size(); i++){
            Long permissionID = (Long) ((ArrayList) exists).get(i);
            sysRolePermissionJpaDAO.deleteByUserIdAndRoleId(editRolePermissiomForm.getRoleId(),permissionID);
        }
        for (int i = 0 ; i < exists1.size(); i++){
            Long permissionID = (Long) ((ArrayList) exists1).get(i);
            SysRolePermissionDO add = new SysRolePermissionDO();
            add.setRoleId(editRolePermissiomForm.getRoleId());
            add.setPermissionId(permissionID);
            sysRolePermissionJpaDAO.save(add);
        }

    }


}
