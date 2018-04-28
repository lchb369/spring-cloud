package com.springcloud.lab.feedservice.dao.jpa;

import com.springcloud.lab.feedservice.dao.jpa.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;

/**
 * Created by lchb on 2017-8-18.
 */


public interface FeedRepository extends JpaRepository<Feed, Long>,JpaSpecificationExecutor<Feed> {

    Feed findFirstByUserIdAndTitleLikeOrderByCreateTimeDesc(Integer userId,String title);

    Page<Feed> findTop100ByCreateTimeBefore(Date createTime, Pageable pageable);

    @Query("select f from Feed f where f.userId=?1")
    List<Feed> queryByUserIdUseSql(Integer userId);

    @Modifying
    @Query("update Feed f set f.content=:content where f.userId=:userId")
    Feed updateUseSql(@Param("content") String content,@Param("userId")Integer userId);

    @Query( value = "select * from feed  where user_id=?1", nativeQuery = true)
    List<Feed> queryByUserIdUseNativeSql(Integer userId);

}