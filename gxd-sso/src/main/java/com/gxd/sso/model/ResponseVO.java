package com.gxd.sso.model;

/**
 * @Author:gxd
 * @Description:
 * @Date: 18:39 2018/1/3
 * @Modified By:
 */
public class ResponseVO {
    // 成员变量
    private int code; //状态码
    private String message; //返回消息
    private Object data;
    public ResponseVO(int code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
