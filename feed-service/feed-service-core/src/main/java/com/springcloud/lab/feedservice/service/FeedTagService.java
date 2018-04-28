package com.springcloud.lab.feedservice.service;

import com.springcloud.lab.api.dto.result.FeedTagResult;
import com.springcloud.lab.api.enums.ExceptionEnum;
import com.springcloud.lab.feedservice.dao.jpa.FeedTagRepository;
import com.springcloud.lab.feedservice.dao.jpa.entity.FeedTag;
import com.springcloud.lab.feedservice.exception.ServerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lchb on 2017-9-14.
 */
@Service
public class FeedTagService {

    @Autowired
    FeedTagRepository feedTagRepository;
    /**
     * @return
     */
    public List<FeedTag> saveFeedTags(Long feedId,String tags){

        if(feedId==null||feedId<=0){
            throw new ServerException(ExceptionEnum.FEED_ID_EMPTY);
        }

        this.clearTagsByFeedId(feedId);
        if(tags==null||tags.trim().length()<=0){
            return null;
        }

        List<String> tagNames=Arrays.asList(tags.split(","));
        List<FeedTag> feedTags=feedTagRepository.findByFeedId(feedId);

        //删除多余的
        List<FeedTag> tagList=new ArrayList<FeedTag>();
        for (String tagName:tagNames){
            FeedTag feedTag=new FeedTag();
            feedTag.setFeedId(feedId);
            feedTag.setName(tagName);
            tagList.add(feedTag);
        }

        List<FeedTag> feedTagList=feedTagRepository.save(tagList);
        return feedTagList;
    }


    public void clearTagsByFeedId(Long feedId){
        feedTagRepository.deleteByFeedId(feedId);
    }

    /**
     * 根据FeedId查询tags
     * @param feedId
     * @return
     */
    public List<FeedTag> getTagListByFeedId(Long feedId){
        List<FeedTag> feedTags=feedTagRepository.findByFeedId(feedId);
        return feedTags;
    }

    public List<FeedTagResult> convertResult(List<FeedTag> tagList){
        List<FeedTagResult> resultList=new ArrayList<FeedTagResult>();
        for(FeedTag feedTag:tagList){
            FeedTagResult tagResult=new FeedTagResult();
            BeanUtils.copyProperties(feedTag,tagResult);
            resultList.add(tagResult);
        }
        return resultList;
    }

}
