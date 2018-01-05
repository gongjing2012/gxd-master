package com.gxd.common.shiro;

import com.gxd.common.constants.Constants;
import com.gxd.common.exception.BusinessException;
import com.gxd.common.salt.Encodes;
import com.gxd.model.auth.PermissionVo;
import com.gxd.model.auth.Role;
import com.gxd.model.auth.User;
import com.gxd.common.shiro.vo.Principal;
import com.gxd.web.remote.auth.AuthRemoteClient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author:gxd
 * @Description:
 * @Date: 14:45 2017/12/27
 */
public class AuthorizingRealmImpl extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(AuthorizingRealmImpl.class);

    @Autowired
    private AuthRemoteClient authRemoteClient;

    /**
     * 登录认证
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("## 正在验证用户登录...");
            }
            //UsernamePasswordToken对象用来存放提交的登录信息
            UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
            String username = token.getUsername();

            if (StringUtils.isBlank(username)) {
                log.error("## 非法登录 .");
                throw new BusinessException("user.illegal.login.error", "非法用户身份");
            }
            // User user = findUserByName(username);
            User user = authRemoteClient.findUserByName(username);

            if (null == user) {
                log.error("## 用户不存在={} .", username);
                throw new BusinessException("user.login.error", "账号或密码错误");
            }

            byte[] salt = Encodes.decodeHex(user.getSalt());

            Principal principal = new Principal();
            principal.setUser(user);
            // principal.setRoles(findRoleByUserId(user.getId()));
            principal.setRoles(authRemoteClient.findRoleByUserId(user.getId()));

            // SecurityUtils.getSubject().getSession().setAttribute(Constants.PERMISSION_SESSION, getPermissions(user.getId()));
            SecurityUtils.getSubject().getSession().setAttribute(Constants.PERMISSION_SESSION, authRemoteClient.getPermissions(user.getId()));

            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, user.getPassword(), ByteSource.Util.bytes(salt), getName());
            return info;
        } catch (AuthenticationException e) {
            log.error("# doGetAuthenticationInfo error , message={}", e.getMessage());
            throw e;
        }

    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     * @see 经测试：本例中该方法的调用时机为需授权资源被访问时
     * @see 经测试：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * @see 经测试：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     */
    @SuppressWarnings("unchecked")
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
        Session session = SecurityUtils.getSubject().getSession();
        // ---
        Set<String> permissions = new HashSet<String>();
        Object permisObj = session.getAttribute(Constants.PERMISSION_URL);
        if (null == permisObj) {
            // Collection<PermissionVo> pers = getPermissions(principal.getUser().getId());
            Collection<PermissionVo> pers = authRemoteClient.getPermissions(principal.getUser().getId());
            for (PermissionVo permission : pers) {
                permissions.add(permission.getUrl());
                if (CollectionUtils.isNotEmpty(permission.getChildren())) {
                    for (PermissionVo childrenPer : permission.getChildren()) {
                        permissions.add(childrenPer.getUrl());
                    }
                }
            }
            session.setAttribute(Constants.PERMISSION_URL, permissions);
        } else {
            permissions = (Set<String>) permisObj;
        }

        Set<String> roleCodes = new HashSet<String>();
        Object roleNameObj = session.getAttribute(Constants.ROLE_CODE);
        if (null == roleNameObj) {
            // for (Role role : findRoleByUserId(principal.getUser().getId())) {
            for (Role role : authRemoteClient.findRoleByUserId(principal.getUser().getId())) {
                roleCodes.add(role.getCode());
            }
            session.setAttribute(Constants.ROLE_CODE, roleCodes);
        } else {
            roleCodes = (Set<String>) roleNameObj;
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleCodes);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-1");
        matcher.setHashIterations(1024);
        setCredentialsMatcher(matcher);
    }

}
