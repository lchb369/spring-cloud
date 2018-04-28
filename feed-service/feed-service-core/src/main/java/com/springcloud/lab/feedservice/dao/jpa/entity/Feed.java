package com.springcloud.lab.feedservice.dao.jpa.entity;

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
public class Feed {

    @Id
    @GeneratedValue
    private Long id;

    private Integer userId;

    private String title;

    private Integer type;

    private String content;

    private String author;

    private Date createTime;

    private Date updateTime;

    public void init(){
        this.setTitle("test");
        this.setUserId(1000);
    }

}
