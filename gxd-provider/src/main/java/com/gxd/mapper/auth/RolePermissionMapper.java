package com.gxd.mapper.auth;

import com.gxd.common.model.auth.RolePermission;
import com.gxd.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:gxd
 * @Description:角色与菜单关系Mapper
 * @Date: 13:47 2017/12/27
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<String, RolePermission> {

    public RolePermission findRolePermission(RolePermission per);

}
