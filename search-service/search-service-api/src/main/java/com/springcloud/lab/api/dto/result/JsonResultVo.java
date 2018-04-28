package com.springcloud.lab.api.dto.result;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by admin on 2017/7/23.
 */
public class JsonResultVo {
    public static final int SUCCESS=200;

    public static final String SUCCESS_MESSAGE="Success";

    protected int status=SUCCESS;

    protected String message;

    protected Object data;

    public JsonResultVo(){

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static JsonResultVo buildSuccessVo(){
        JsonResultVo vo=new JsonResultVo();
        vo.setStatus(SUCCESS);
        vo.setMessage(SUCCESS_MESSAGE);
        return vo;
    }

    public static JsonResultVo buildSuccessVo(String message){
        JsonResultVo vo=new JsonResultVo();
        vo.setStatus(SUCCESS);
        vo.setMessage(message);
        return vo;
    }

    public static JsonResultVo buildResultVo(Object data){
        JsonResultVo vo=buildSuccessVo();
        vo.setStatus(SUCCESS);
        vo.setData(data);
        return vo;
    }


    public static JsonResultVo buildErrorVo(int status, String message){
        JsonResultVo vo=new JsonResultVo();
        vo.setStatus(status);
        vo.setMessage(message);
        return vo;
    }

    public <T> T toBean(Class<T> clazz) {
        JSONObject jsonObject=(JSONObject) this.getData();
        T object=jsonObject.toJavaObject(clazz);
        return object;
    }
}
