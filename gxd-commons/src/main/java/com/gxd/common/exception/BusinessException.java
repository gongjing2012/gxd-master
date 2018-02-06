package com.gxd.common.exception;

import com.gxd.common.model.Result;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1144969267587138347L;

    public BusinessException(String code, String message, Exception cause) {
        super(code + ":" + message, cause);
    }

    public BusinessException(String code, String message) {
        super(code + ":" + message);
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
    /**
     * <pre>
     * 抛出业务逻辑异常信息
     * </pre>
     */
    public static void throwMessage(String errCode, String... params) {
        throw new BusinessException(String.valueOf(Result.INPUT_AUTO), errCode);
    }

    /**
     * <pre>
     * 抛出业务逻辑异常信息
     * </pre>
     */
    public static void throwMessageWithCode(String errCode, String message) {
        throw new BusinessException(errCode, message);
    }

    /**
     * <pre>
     * 抛出业务逻辑异常信息
     * </pre>
     */
    public static void throwResult(Result result) {
        String errCode = String.valueOf(result.getState());
        String message = result.getMessage();
        throw new BusinessException(errCode, message);
    }
}
