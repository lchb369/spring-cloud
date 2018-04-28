package com.springcloud.lab.searchservice.service;

import com.springcloud.lab.api.dto.params.FeedQueryRequest;
import com.springcloud.lab.api.dto.result.FeedResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Created by admin on 2017/7/23.
 * 查询文档:http://blog.csdn.net/tianyaleixiaowu/article/details/77965257
 */
@Service
public class FeedSearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    //http://blog.csdn.net/tianyaleixiaowu/article/details/77965257
    public FeedResult queryList(FeedQueryRequest request){

        return null;
    }

}
