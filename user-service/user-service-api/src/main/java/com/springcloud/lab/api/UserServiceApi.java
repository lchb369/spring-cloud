package com.springcloud.lab.api;

import com.springcloud.lab.api.dto.params.UserParams;
import com.springcloud.lab.api.dto.result.JsonResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("user-service")
@RequestMapping(value = "user/v1/")
public interface UserServiceApi {

    @ApiOperation("创建／修改User")
    @RequestMapping(value = "save",method = RequestMethod.POST)
    JsonResultVo save(@RequestBody UserParams userParams);

    /**
     * 删除User
     * @return
     */
    @ApiOperation("删除User")
    @RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
    JsonResultVo delete(@PathVariable("id") Integer id);

    /**
     * 获取详情
     * @param id
     * @return
     */
    @ApiOperation("查看详情")
    @RequestMapping(value = "detail/{id}",method = RequestMethod.GET)
    JsonResultVo detail(@PathVariable("id") Integer id);
}
