package com.springcloud.lab.api.dto.result;

import lombok.Data;

import java.util.Date;

/**
 * Created by lchb on 2017-9-14.
 */
@Data
public class UserDetailResult {

    private Integer id;

    private String name;

    private Integer age;

    private String phone;

    private Date createTime;

    private Date updateTime;
}
