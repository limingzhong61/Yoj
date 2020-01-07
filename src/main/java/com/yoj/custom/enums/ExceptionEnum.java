package com.yoj.custom.enums;

public enum ExceptionEnum {
    UNKNOWN_ERROR(-1, "未知异常"),
    // 身份未认证，请求拒绝
    NEED_LOGIN(401,"need login,authenticate fail,reject request"), // Unauthorized（未授权）
    //权限不足
    NOT_ACCESS(403,"权限不足"),
    HttpRequestMethodNotSupportedException(500,"HttpRequestMethodNotSupportedException"),
    MethodArgumentTypeMismatchException(500,"MethodArgumentTypeMismatchException");
    private Integer state;
    private String msg;

    ExceptionEnum(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public Integer getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}
