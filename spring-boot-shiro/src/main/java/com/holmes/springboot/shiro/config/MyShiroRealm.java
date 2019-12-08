package com.holmes.springboot.shiro.config;

import com.holmes.springboot.shiro.entity.Permission;
import com.holmes.springboot.shiro.entity.User;
import com.holmes.springboot.shiro.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        log.info("doGetAuthenticationInfo");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();

        User user = shiroService.getUserByName(userName);
        if (null == user) {
            throw new UnknownAccountException("用户不存在");
        }
        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(user, user.getPassword(), getName());

        return authenticationInfo;
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        log.info("doGetAuthorizationInfo");

        User user = (User) SecurityUtils.getSubject().getPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        for (Permission permission : user.getPermissionList()) {
            authorizationInfo.addStringPermission(permission.getPermissionUrl());
        }

        return authorizationInfo;
    }
}
