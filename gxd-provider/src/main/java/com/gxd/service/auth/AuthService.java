package com.gxd.service.auth;

import com.gxd.common.model.auth.Role;
import com.gxd.common.model.auth.User;

import java.util.List;

/**
 * @Author:gxd
 * @Description:组装权限接口
 * @Date: 13:51 2017/12/27
 */
public interface AuthService {

    /**
     * 根据用户名查询用户
     *
     * @param username
     *            用户名
     * @return user 用户
     */
    public User findUserByName(String username);

    /**
     * 根据角色编码查询角色
     * 
     * @param roleCode
     *            角色编码
     * @return 角色对象
     */
    public Role findRoleByRoleCode(String roleCode);

    /**
     * 根据角色编码查询用户
     *
     * @param roleCode
     *            角色编码
     * @return user 用户
     */
    public List<User> findUserByRoleCode(String roleCode);

}
