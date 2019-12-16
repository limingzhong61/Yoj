package com.yoj.custom.enums;

public enum ExceptionEnum {
    UNKNOWN_ERROR(-1, "未知异常"),
    NEED_LOGIN(401,"need login"), // Unauthorized（未授权）
    NOT_ACCESS(403,"权限不足");
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
