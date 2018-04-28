package com.springcloud.lab.api;

import com.springcloud.lab.api.dto.params.FeedQueryRequest;
import com.springcloud.lab.api.dto.params.FeedRequest;
import com.springcloud.lab.api.dto.result.JsonResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@Api(description="Feed接口")
@FeignClient("search-service")
public interface SearchServiceApi {

    /**
     * 获取列表
     * @param requestVo
     * @return
     */
    @ApiOperation("获取列表")
    @RequestMapping(value = "search/v1/list",method = RequestMethod.GET)
    JsonResultVo list(@RequestBody FeedQueryRequest requestVo);

}
