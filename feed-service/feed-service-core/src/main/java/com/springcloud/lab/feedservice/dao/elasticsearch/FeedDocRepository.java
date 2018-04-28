package com.springcloud.lab.feedservice.dao.elasticsearch;

import com.springcloud.lab.feedservice.dao.elasticsearch.docs.FeedDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
/**
 * Created by lchb on 2017-8-18.
 */
//http://blog.csdn.net/tianyaleixiaowu/article/details/77965257
public interface FeedDocRepository extends ElasticsearchRepository<FeedDoc,Integer> {

    List<FeedDoc> findByTitleLike(String name);
}