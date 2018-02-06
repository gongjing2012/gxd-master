package com.gxd.mapper.auth;

import com.gxd.mapper.BaseMapper;
import com.gxd.common.model.auth.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:gxd
 * @Description:用户与角色关系Mapper
 * @Date: 13:49 2017/12/27
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<String, UserRole> {

}
