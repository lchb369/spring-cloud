package com.springcloud.lab.feedservice.dao.elasticsearch.docs;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.*;
import javax.persistence.Id;
import java.io.Serializable;
/**
 * Created by admin on 2017/7/23.
 */
@Data
@Document(indexName="feed_index",type="feedDoc",shards=5,replicas=1,indexStoreType="fs",refreshInterval="-1")
public class FeedDoc implements Serializable{

    private static final long serialVersionUID = 551589397625941750L;

    @Id
    private Integer id;

    private String title;

    private Integer type;

    private String content;

    private String author;
}
