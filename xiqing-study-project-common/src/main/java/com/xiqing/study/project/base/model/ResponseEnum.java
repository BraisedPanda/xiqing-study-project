package com.xiqing.study.project.base.model;

public enum ResponseEnum {
    SUCCESS(200, "请求成功"),
    FILE_NOT_FOUND(400, "文件不存在"),
    FAILED(500, "请求失败")
    ;

    private Integer code;
    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseEnum{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
