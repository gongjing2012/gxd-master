package com.gxd.common.exception;

public class ArgumentException extends IllegalArgumentException {

    protected ErrorCode errorCode;

    public ArgumentException(ErrorCode errorCode) {
        super(formatMsg(errorCode));
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public final static String formatMsg(ErrorCode errorCode) {
        return String.format("%s:%s", errorCode.getCode(), errorCode.getMessage());
    }
}
