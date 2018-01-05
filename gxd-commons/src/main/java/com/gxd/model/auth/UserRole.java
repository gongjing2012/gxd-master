package com.gxd.model.auth;

import com.gxd.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author:gxd
 * @Description:用户与角色关系对象
 * @Date: 11:55 2017/12/25
 */
@ApiModel(value = "UserRole", description = "用户与角色关系对象")
public class UserRole extends BaseEntity<String> {

    private static final long serialVersionUID = -56720255622342923L;

    @ApiModelProperty(name = "id", value = "ID", dataType = "String")
    private String id;

    @ApiModelProperty(name = "userId", value = "用户ID", dataType = "String")
    private String userId;

    @ApiModelProperty(name = "roleId", value = "角色ID", dataType = "String")
    private String roleId;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
