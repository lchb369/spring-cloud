package com.springcloud.lab.api.dto.params;

import lombok.Data;

/**
 * Created by admin on 2017/7/23.
 */
@Data
public class FeedQueryRequest {

    private Integer id;

    private String title;

    private Integer type;

    private String author;

    private String tag;

    private String createTime;

    private Integer page=1;

    private Integer limit=10;

    private Integer nativeQuery=0;

}
