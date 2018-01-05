package com.gxd.enums;

import com.gxd.model.ResponseVO;

/**
 * @Author:gxd
 * @Description:
 * @Date: 18:43 2018/1/3
 * @Modified By:
 */
public enum ResponseCode {
    REQUEST_URL_NOT_SERVICE(4001,"服务不存在");
    // 成员变量
    private int code; //状态码
    private String message; //返回消息
    // 构造方法
    private ResponseCode(int code,String message) {
        this.code = code;
        this.message = message;
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
    public static ResponseVO buildEnumResponseVO(ResponseCode responseCode, Object data) {
        return new ResponseVO(responseCode.getCode(),responseCode.getMessage(),data);
    }
}
