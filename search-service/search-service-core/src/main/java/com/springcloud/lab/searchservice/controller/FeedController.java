
package com.springcloud.lab.searchservice.controller;


import com.springcloud.lab.api.SearchServiceApi;
import com.springcloud.lab.api.dto.params.FeedQueryRequest;
import com.springcloud.lab.api.dto.result.FeedResult;
import com.springcloud.lab.api.dto.result.JsonResultVo;
import com.springcloud.lab.searchservice.service.FeedSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by admin on 2017/7/9.
 */

@RestController
public class FeedController extends BaseController implements SearchServiceApi{

    @Autowired
    FeedSearchService feedSearchService;
    /**
     * 获取列表
     * @param requestVo
     * @return
     */
    public JsonResultVo list(FeedQueryRequest requestVo){
        FeedResult feedResultVo=feedSearchService.queryList(requestVo);
        return JsonResultVo.buildResultVo(feedResultVo);
    }

}
