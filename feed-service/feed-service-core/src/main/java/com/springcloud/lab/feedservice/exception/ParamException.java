package com.springcloud.lab.feedservice.exception;

import com.springcloud.lab.api.enums.ExceptionEnum;

/**
 * Created by admin on 2017/7/23.
 */
public class ParamException extends RuntimeException{

    private static final long serialVersionUID=-1L;

    private Integer code;

    public ParamException(ExceptionEnum exceptionEnum){
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
