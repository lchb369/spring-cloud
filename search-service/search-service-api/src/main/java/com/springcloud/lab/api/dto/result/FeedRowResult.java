package com.springcloud.lab.api.dto.result;
import lombok.Data;

import java.util.Date;

/**
 * Created by lchb on 2017-9-14.
 */
@Data
public class FeedRowResult {

    private Long id;

    private Integer userId;

    private String title;

    private Integer type;

    private String author;

    private Date createTime;

    private Date updateTime;
}
