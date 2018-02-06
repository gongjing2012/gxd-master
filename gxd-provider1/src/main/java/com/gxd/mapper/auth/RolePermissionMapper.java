package com.gxd.mapper.auth;

import com.gxd.mapper.BaseMapper;
import com.gxd.common.model.auth.RolePermission;
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
