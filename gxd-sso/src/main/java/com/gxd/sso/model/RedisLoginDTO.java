package com.gxd.sso.model;

import java.io.Serializable;

/**
 * @Author:gxd
 * @Description:
 * @Date: 18:08 2018/1/3
 * @Modified By:
 */
public class RedisLoginDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8116817810829835862L;

    /**
     * 用户id
     */
    private String uid;

    /**
     * jwt生成的token信息
     */
    private String token;

    /**
     * 登录或刷新应用的时间
     */
    private long refTime;

    public RedisLoginDTO(){

    }

    public RedisLoginDTO(String uid, String token, long refTime){
        this.uid = uid;
        this.token = token;
        this.refTime = refTime;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public long getRefTime() {
        return refTime;
    }
    public void setRefTime(long refTime) {
        this.refTime = refTime;
    }



}
