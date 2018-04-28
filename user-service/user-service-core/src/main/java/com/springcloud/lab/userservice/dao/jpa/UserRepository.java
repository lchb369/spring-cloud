package com.springcloud.lab.userservice.dao.jpa;

import com.springcloud.lab.userservice.dao.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lchb on 2017-8-18.
 */


public interface UserRepository extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User> {

}