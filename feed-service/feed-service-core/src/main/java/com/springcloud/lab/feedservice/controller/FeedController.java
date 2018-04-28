
package com.springcloud.lab.feedservice.controller;

import com.springcloud.lab.api.FeedServiceApi;
import com.springcloud.lab.api.UserServiceApi;
import com.springcloud.lab.api.dto.result.FeedResult;
import com.springcloud.lab.api.dto.params.FeedQueryRequest;
import com.springcloud.lab.api.dto.params.FeedRequest;
import com.springcloud.lab.api.dto.result.FeedDetailResult;
import com.springcloud.lab.api.dto.result.JsonResultVo;
import com.springcloud.lab.api.dto.result.UserDetailResult;
import com.springcloud.lab.feedservice.dao.elasticsearch.docs.FeedDoc;
import com.springcloud.lab.feedservice.service.FeedSearchService;
import com.springcloud.lab.feedservice.service.FeedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by admin on 2017/7/9.
 */

@RestController
public class FeedController extends BaseController implements FeedServiceApi{

    @Autowired
    FeedService feedService;

    @Autowired
    FeedSearchService feedSearchService;

    @Autowired
    UserServiceApi userServiceApi;

    /**
     * 保存Feed
     * @param feedRequestVo
     * @return
     */
    public JsonResultVo save(@Valid FeedRequest feedRequestVo){
        FeedDetailResult detailResultVo=feedService.saveFeed(feedRequestVo);
        return JsonResultVo.buildResultVo(detailResultVo);
    }

    /**
     * 删除Feed
     * @return
     */
    public JsonResultVo delete(@PathVariable Long id){
        feedService.deleteFeed(id);
        return JsonResultVo.buildSuccessVo();
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    public JsonResultVo detail(@PathVariable Long id){


        System.out.println("=======detail=======");

        JsonResultVo jsonResultVo=userServiceApi.detail(1);
        UserDetailResult detailResult=jsonResultVo.toBean(UserDetailResult.class);

        //UserDetailResult detailResult=userServiceApi.detail(1).toBean(UserDetailResult.class);

        System.out.println("status====="+jsonResultVo.getStatus());
        System.out.println("message====="+jsonResultVo.getMessage());
        System.out.print("name====="+detailResult.getName());

        FeedDetailResult detailResultVo=feedService.getDetail(id);
        return JsonResultVo.buildResultVo(detailResultVo);
    }

    /**
     * 获取列表
     * @param requestVo
     * @return
     */
    public JsonResultVo list(FeedQueryRequest requestVo){
        FeedResult feedResultVo=feedService.queryList(requestVo);
        return JsonResultVo.buildResultVo(feedResultVo);
    }

    /**
     * 获取列表
     * @param requestVo
     * @return
     */
    public JsonResultVo search(FeedQueryRequest requestVo) {
        List<FeedDoc> feedDocList = feedSearchService.search(requestVo);
        return JsonResultVo.buildResultVo(feedDocList);
    }

}
