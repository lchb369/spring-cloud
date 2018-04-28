package com.springcloud.lab.feedservice.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;


public class ResponseEntity<T> implements Serializable{

    Integer status;

    String message;

    String data;

    T object;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public void setData(String data) {
        this.data = data;
    }


    public <T> ResponseEntity(
            @JsonProperty("status") Integer status,
            @JsonProperty("message") String message,
            @JsonProperty("data")  String data
    ) {
        this.status = status;
        this.message = message;
        this.data = data;

        ObjectMapper mapper = new ObjectMapper();
    }
}
