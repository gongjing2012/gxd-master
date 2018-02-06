package com.gxd.sso.enums;

/**
 * @Author:gxd
 * @Description:
 * @Date: 1:56 2018/1/6
 * @Modified By:
 */
public enum MemberResponseCode {
    RESPONSE_CODE_USER_NAME_IS_NOT_EMPTY(5001, "用户名不能为空"),
    RESPONSE_CODE_PWD_CAN_NOT_BE_EMPTY(5002, "密码不能为空"),
    RESPONSE_CODE_USER_VALIDATE_NO_SUCCESS(5003, "用户不存在"),
    RESPONSE_CODE_USER_PWD_NO_SUCCESS(5004, "密码不对");
    // 成员变量
    private int code; //状态码
    private String message; //返回消息

    // 构造方法
    private MemberResponseCode(int code, String message) {
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
}
