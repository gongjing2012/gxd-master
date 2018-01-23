package com.gxd.sso.model;

import java.io.Serializable;

/**
 * @Author:gxd
 * @Description:
 * @Date: 18:07 2018/1/3
 * @Modified By:
 */
public class LoginDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1899232511233819216L;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 登录用户名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String password;

    public LoginDTO(){
        super();
    }

    public LoginDTO(String uid, String loginName, String password){
        this.uid = uid;
        this.loginName = loginName;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


}
