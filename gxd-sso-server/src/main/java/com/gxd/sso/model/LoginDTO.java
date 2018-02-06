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
    private String loginAccount;

    /**
     * 登录密码
     */
    private String password;

    public LoginDTO(){
        super();
    }

    public LoginDTO(String uid, String loginName, String password){
        this.uid = uid;
        this.loginAccount = loginName;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


}
