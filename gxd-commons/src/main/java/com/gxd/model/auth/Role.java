package com.gxd.model.auth;

import com.gxd.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author:gxd
 * @Description:角色对象
 * @Date: 11:49 2017/12/27
 */
@ApiModel(value = "Role", description = "角色实体类")
public class Role extends BaseEntity<String> {

    private static final long serialVersionUID = -6982490361440305761L;

    @ApiModelProperty(name = "id", value = "ID", dataType = "String")
    private String id;

    @ApiModelProperty(name = "name", value = "角色名称", dataType = "String")
    private String name;

    @ApiModelProperty(name = "code", value = "编码", dataType = "String")
    private String code;

    @ApiModelProperty(name = "remark", value = "备注", dataType = "String")
    private String remark;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
