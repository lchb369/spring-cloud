package com.springcloud.lab.userservice.dao.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by admin on 2017/7/23.
 */
@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Integer age;

    private String phone;

    private Date createTime;

    private Date updateTime;

}
