package com.springcloud.lab.userservice.exception;

/**
 * Created by admin on 2017/7/23.
 */
public class ServerException extends RuntimeException{

    private static final long serialVersionUID=-1L;

    private Integer code;

    public ServerException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMessage());
        this.code=exceptionEnum.getCode();
    }

    public Integer getCode(){
        return code;
    }

    public void setCode(Integer code){
        this.code=code;
    }

}
