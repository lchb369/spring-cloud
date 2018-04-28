package com.springcloud.lab.api;

import com.springcloud.lab.api.dto.params.FeedQueryRequest;
import com.springcloud.lab.api.dto.params.FeedRequest;
import com.springcloud.lab.api.dto.result.JsonResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@Api(description="Feed接口")
@FeignClient("feed-service")
public interface FeedServiceApi {

    @ApiOperation("发布/修改Feed")
    @RequestMapping(value = "feed/v1/save",method = RequestMethod.POST)
    JsonResultVo save(@RequestBody FeedRequest feedRequest);


    /**
     * 删除Feed
     * @return
     */
    @ApiOperation("删除Feed")
    @RequestMapping(value = "feed/v1/delete/{id}",method = RequestMethod.DELETE)
    JsonResultVo delete(@PathVariable("id") Long id);


    /**
     * 获取详情
     * @param id
     * @return
     */
    @ApiOperation("查看详情")
    @RequestMapping(value = "feed/v1/detail/{id}",method = RequestMethod.GET)
    JsonResultVo detail(@PathVariable("id") Long id);


    /**
     * 获取列表
     * @param requestVo
     * @return
     */
    @ApiOperation("获取列表")
    @RequestMapping(value = "feed/v1/list",method = RequestMethod.GET)
    JsonResultVo list(@RequestBody FeedQueryRequest requestVo);


    /**
     * 获取列表
     * @param requestVo
     * @return
     */
    @ApiOperation("搜索列表")
    @RequestMapping(value = "feed/v1/search",method = RequestMethod.GET)
    JsonResultVo search(@RequestBody FeedQueryRequest requestVo);

}
