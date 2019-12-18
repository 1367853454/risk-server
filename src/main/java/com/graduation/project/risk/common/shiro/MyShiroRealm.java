package com.graduation.project.risk.common.shiro;

import com.graduation.project.risk.project.dal.jpa.dao.sys.SysUserJpaDAO;
import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysPermissionDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysRoleDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysUserDO;
import com.graduation.project.risk.project.dal.mybaits.dao.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserJpaDAO sysUserJpaDAO;
    @Autowired
    private UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        system.out.println("-->MyShiroRealm.doGetAuthorizationInfo()");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUserDO sysUserDO = (SysUserDO) principals.getPrimaryPrincipal();

        List<SysRoleDO> sysRoleDOS = userMapper.getSysRoleByUserId(sysUserDO.getId());
        if (sysRoleDOS != null && sysRoleDOS.size()>0)
            for (SysRoleDO sysRoleDO:sysRoleDOS
            ) {
                authorizationInfo.addRole(sysRoleDO.getName());
            }

        List<SysPermissionDO> sysPermissionDOS = userMapper.getPermissionByUserId(sysUserDO.getId());
        if (sysPermissionDOS != null && sysPermissionDOS.size()>0)
            for (SysPermissionDO sysPermissionDO:sysPermissionDOS
                 ) {
                authorizationInfo.addStringPermission(sysPermissionDO.getPermission());
            }

        return authorizationInfo;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
//        system.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        //get user's account
        String username = (String) token.getPrincipal();
//        system.out.println(token.getCredentials());
        SysUserDO sysUserDO = userMapper.getUserByAccount(username);
//        system.out.println("----->>userInfo=" + sysUserDO);
        if (sysUserDO == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                sysUserDO,
                sysUserDO.getPassword(),
//                ByteSource.Util.bytes("123"),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}