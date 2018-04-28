package com.springcloud.lab.feedservice.dao.jpa;

import com.springcloud.lab.feedservice.dao.jpa.entity.FeedTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by lchb on 2017-8-18.
 */
public interface FeedTagRepository extends JpaRepository<FeedTag, Integer>{

    //feedId=? and name not in()
    List<FeedTag> findByFeedIdAndNameIn(Long feedId,List<String> names);

    //feedId=?
    List<FeedTag> findByFeedId(Long feedId);

    List<FeedTag> deleteByFeedId(Long feedId);
}