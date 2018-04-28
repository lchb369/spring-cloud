package com.springcloud.lab.api.enums;

/**
 * Created by admin on 2017/7/23.
 */
public enum ExceptionEnum {

    SUCCESS(            200,    "成功"),
    PARAM_ERROR(        4000,   "参数错误"),
    SERVER_ERROR(       5000,   "服务器错误"),

    //--参数类错误40**
    PARAM_CANNOT_EMPTY(    4001,   "参数不正确"),

    //--服务端错误50**
    FEED_ID_EMPTY(         5001,   "FEED ID不能为空");

    private final Integer code;

    private final String message;

    ExceptionEnum(Integer code, String message){
        this.code=code;
        this.message=message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
