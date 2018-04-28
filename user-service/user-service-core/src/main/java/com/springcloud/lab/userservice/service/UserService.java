package com.springcloud.lab.userservice.service;

import com.springcloud.lab.api.dto.params.UserParams;
import com.springcloud.lab.api.dto.result.UserDetailResult;
import com.springcloud.lab.userservice.dao.jpa.NativeQueryRepository;
import com.springcloud.lab.userservice.dao.jpa.UserRepository;

import com.springcloud.lab.userservice.dao.jpa.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by lchb on 2017-9-14.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NativeQueryRepository nativeQueryRepository;

    /**
     * 保存数据
     * @param
     */
    @Transactional(rollbackFor = { Exception.class })
    public UserDetailResult saveUser(UserParams userParams){

        User user=null;
        if(userParams.getId()!=null&&userParams.getId()>0){
            user=userRepository.findOne(userParams.getId());
        }

        if(user==null){
            user=new com.springcloud.lab.userservice.dao.jpa.entity.User();
            user.setCreateTime(new Date());
        }
        user.setUpdateTime(new Date());

        BeanUtils.copyProperties(userParams,user);
        user=userRepository.save(user);

        //推送到MQ
        UserDetailResult detailResult=new UserDetailResult();
        BeanUtils.copyProperties(user,detailResult);

        return detailResult;
    }

    /**
     * 删除User
     * @param userId
     */
    public void deleteFeed(Integer userId){
        userRepository.delete(userId);
    }

    /**
     * 获取详情
     * @param userId
     * @return
     */
    public UserDetailResult getDetail(Integer userId){
        User user=userRepository.findOne(userId);
        UserDetailResult detailResult=new UserDetailResult();
        BeanUtils.copyProperties(user,detailResult);
        return detailResult;
    }

}
