
package com.springcloud.lab.userservice.controller;

import com.springcloud.lab.userservice.exception.ExceptionEnum;
import com.springcloud.lab.api.dto.result.JsonResultVo;
import com.springcloud.lab.userservice.exception.ParamException;
import com.springcloud.lab.userservice.exception.ServerException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by admin on 2017/7/9.
 */
public class BaseController {

    @ExceptionHandler
    @ResponseBody
    public JsonResultVo exceptionHandler(Exception e){

        if(e instanceof ParamException){
            ParamException paramException=(ParamException)e;
            return JsonResultVo.buildErrorVo(paramException.getCode(),paramException.getMessage());
        }else if(e instanceof ServerException){
            ServerException serverException=(ServerException)e;
            return JsonResultVo.buildErrorVo(serverException.getCode(),serverException.getMessage());
        }else if(e instanceof BindException) {
            //@Valid注解
            BindException bindException=(BindException)e;
            List<FieldError> fieldErrors=bindException.getFieldErrors();
            for (FieldError error:fieldErrors){
                return JsonResultVo.buildErrorVo(ExceptionEnum.PARAM_ERROR.getCode(),error.getDefaultMessage());
            }

        }else{
            return JsonResultVo.buildErrorVo(ExceptionEnum.SERVER_ERROR.getCode(),e.getMessage());
        }
        return null;
    }
}
