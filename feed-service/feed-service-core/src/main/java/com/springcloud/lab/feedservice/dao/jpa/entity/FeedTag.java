package com.springcloud.lab.feedservice.dao.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by lchb on 2017-9-14.
 */
@Entity
@Data
public class FeedTag {
    @Id
    @GeneratedValue
    private Integer id;

    private Long feedId;

    private String name;
}
