package com.springcloud.lab.api.dto.result;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by lchb on 2017-9-14.
 */
@Data
public class FeedDetailResult {

    private Long id;

    private Integer userId;

    private String title;

    private Integer type;

    private String author;

    private String content;

    private List<FeedTagResult> feedTagResultList;

    private Date createTime;

    private Date updateTime;
}
