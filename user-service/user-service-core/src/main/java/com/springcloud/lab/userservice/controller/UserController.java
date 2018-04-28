
package com.springcloud.lab.userservice.controller;

import com.springcloud.lab.api.UserServiceApi;
import com.springcloud.lab.api.dto.params.UserParams;
import com.springcloud.lab.api.dto.result.JsonResultVo;
import com.springcloud.lab.api.dto.result.UserDetailResult;
import com.springcloud.lab.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by admin on 2017/7/9.
 */

@RestController
public class UserController extends BaseController implements UserServiceApi{

    @Autowired
    UserService userService;

    /**
     * 保存User
     * @param userParams
     * @return
     */
    public JsonResultVo save(@Valid UserParams userParams){

        UserDetailResult userDetailResult=userService.saveUser(userParams);
        return JsonResultVo.buildResultVo(userDetailResult);
    }

    /**
     * 删除User
     * @return
     */
    public JsonResultVo delete(@PathVariable Integer id){
        userService.deleteFeed(id);
        return JsonResultVo.buildSuccessVo();
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    public JsonResultVo detail(@PathVariable("id") Integer id){
        UserDetailResult detailResult=userService.getDetail(id);
        return JsonResultVo.buildResultVo(detailResult);
    }

}
