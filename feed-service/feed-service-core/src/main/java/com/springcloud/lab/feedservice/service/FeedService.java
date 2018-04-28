package com.springcloud.lab.feedservice.service;

import com.springcloud.lab.api.dto.params.FeedQueryRequest;
import com.springcloud.lab.api.dto.params.FeedRequest;
import com.springcloud.lab.api.dto.result.FeedDetailResult;
import com.springcloud.lab.api.dto.result.FeedResult;
import com.springcloud.lab.api.dto.result.FeedRowResult;
import com.springcloud.lab.feedservice.dao.jpa.FeedRepository;
import com.springcloud.lab.feedservice.dao.jpa.NativeQueryRepository;
import com.springcloud.lab.feedservice.dao.jpa.entity.Feed;
import com.springcloud.lab.feedservice.dao.jpa.entity.FeedTag;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lchb on 2017-9-14.
 */
@Service
public class FeedService {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private NativeQueryRepository nativeQueryRepository;

    @Autowired
    private FeedTagService feedTagService;

    /**
     * 保存数据
     * @param feedRequestVo
     */
    @Transactional(rollbackFor = { Exception.class })
    public FeedDetailResult saveFeed(FeedRequest feedRequestVo){

        Feed feed=null;
        if(feedRequestVo.getId()!=null&&feedRequestVo.getId()>0){
            feed=feedRepository.findOne(feedRequestVo.getId());
        }

        if(feed==null){
            feed=new Feed();
            feed.setCreateTime(new Date());
        }
        feed.setUpdateTime(new Date());

        BeanUtils.copyProperties(feedRequestVo,feed);
        feed=feedRepository.save(feed);
        List<FeedTag> feedTagList=feedTagService.saveFeedTags(feed.getId(),feedRequestVo.getTags());

        //推送到MQ
        FeedDetailResult detailResultVo=new FeedDetailResult();
        BeanUtils.copyProperties(feed,detailResultVo);


        detailResultVo.setFeedTagResultList(feedTagService.convertResult(feedTagList));

        return detailResultVo;
    }


    /**
     * 删除Feed
     * @param feedId
     */
    public void deleteFeed(Long feedId){
        feedRepository.delete(feedId);
    }


    /**
     * 获取详情
     * @param feedId
     * @return
     */
    public FeedDetailResult getDetail(Long feedId){
        Feed feed=feedRepository.findOne(feedId);
        FeedDetailResult detailResultVo=new FeedDetailResult();
        BeanUtils.copyProperties(feed,detailResultVo);

        List<FeedTag> feedTagList=feedTagService.getTagListByFeedId(feedId);
        detailResultVo.setFeedTagResultList(feedTagService.convertResult(feedTagList));

        return detailResultVo;
    }

    /**
     * 查询列表
     * @param request
     */
    public FeedResult queryList(FeedQueryRequest request){

        if(request.getPage()==null||request.getPage()<1){
            request.setPage(1);
        }

        if(request.getLimit()==null||request.getLimit()<1){
            request.setLimit(10);
        }

        List<Feed> feedList;
        long total=0;
        if(request.getNativeQuery()!=1) {

            String orderby = "createTime";
            Sort.Direction sortDirect = Sort.Direction.DESC;
            Sort sort = new Sort(sortDirect, orderby);
            Specification<Feed> specification = getWhereClause(request);
            Page<Feed> all = feedRepository.findAll(specification,
                    new PageRequest(request.getPage()-1, request.getLimit(), sort));
            feedList = all.getContent();
            total= all.getTotalElements();

        }else{
            //原生查询

            /**
             * Select
             */
            StringBuffer sql = new StringBuffer("select * from feed where 1");
            Map<String,Object> parameter=new HashMap<String, Object>();

            /**
             * Where
             */
            StringBuffer andWhere=getNativeWhereClause(parameter,request);
            sql.append(andWhere.toString());

            /**
             * Order By
             */

            String orderby = " create_time DESC ";
            sql.append(" ORDER BY "+orderby);


            /**
             * Limit
             */
            Integer offset=(request.getPage()-1)*request.getLimit();
            sql.append( " LIMIT "+offset+","+request.getLimit());

            //--
            String countSql="select count(*) as total from article_base where 1 "+andWhere.toString();
            total=nativeQueryRepository.findCountBySql(countSql,parameter);
            feedList=nativeQueryRepository.findBySql(sql.toString(),parameter,Feed.class);

        }

        List<FeedRowResult> feedRowResultVos=new ArrayList<FeedRowResult>();
        for(Feed feed:feedList){
            FeedRowResult rowResultVo=new FeedRowResult();
            BeanUtils.copyProperties(feed,rowResultVo);
            feedRowResultVos.add(rowResultVo);
        }

        FeedResult feedResultVo=new FeedResult();
        feedResultVo.setTotal((int)total);
        feedResultVo.setList(feedRowResultVos);
        return feedResultVo;
    }

    /**
     * 动态查询-Specification
     * @param request
     * @return
     */
    private Specification<Feed> getWhereClause(final FeedQueryRequest request) {
        return new Specification<Feed>() {
            @Override
            public Predicate toPredicate(Root<Feed> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicate = new ArrayList<Predicate>();
                query.distinct(true);

                // ID
                if (request.getId() != null && request.getId() > 0) {
                    predicate.add(builder.equal(root.<Integer> get("id"), request.getId()));
                }

                //type
                if (request.getType() != null) {
                    predicate.add(builder.equal(root.<Integer>get("type"), request.getType()));
                }

                // createTime时间查询
                if (request.getCreateTime() != null && request.getCreateTime().length()>0) {
                    predicate = appendPredicateDate("createTime", request.getCreateTime(), predicate, root, builder);
                }

                if (request.getTitle() != null && request.getTitle().length() > 0) {
                    predicate.add(builder.like(root.<String> get("title"), "%" + request.getTitle() + "%"));
                }

                if (request.getAuthor() != null && request.getAuthor().length() > 0) {
                    predicate.add(builder.like(root.<String> get("author"), "%" + request.getAuthor() + "%"));
                }

                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    /**
     * 构建原生查询条件
     * @param parameter
     * @param request
     * @return
     */
    public StringBuffer getNativeWhereClause(Map<String,Object> parameter, FeedQueryRequest request) {


        StringBuffer sql=new StringBuffer("");
        //ID
        if (request.getId() != null && request.getId() > 0) {
            sql.append(" AND id=:id ");
            parameter.put("id",request.getId());
        }

        //type
        if (request.getType() != null) {
            sql.append(" AND type=:type ");
            parameter.put("type",request.getType());
        }

        // createTime时间查询
        if (request.getCreateTime() != null && request.getCreateTime().length()>0) {
            if(appendTimeBetween("createTime",request.getCreateTime(),parameter)) {
                sql.append(" AND create_time BETWEEN :createTimeStart AND :createTimeEnd ");
            }
        }

        if (request.getTitle() != null && request.getTitle().length() > 0) {
            sql.append(" AND title LIKE :title ");
            parameter.put("title","%"+request.getTitle()+"%");
        }

        if (request.getAuthor() != null && request.getAuthor().length() > 0) {
            sql.append(" AND author LIKE :author ");
            parameter.put("author","%"+request.getAuthor()+"%");
        }

        return sql;
    }

    /**
     * 日期条件查询
     * @param timeKey
     * @param date
     * @param parameter
     * @return
     */
    private Boolean appendTimeBetween(String timeKey,String date,Map<String,Object> parameter){
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = format.parse(date);
            endDate = startDate;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.add(Calendar.DATE, 1);
            endDate = calendar.getTime();

            String timeStartKey=timeKey+"Start";
            String timeEndKey=timeKey+"End";

            parameter.put(timeStartKey,startDate);
            parameter.put(timeEndKey,endDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 根据日期查询当天范围
     * @param prop
     * @param date
     * @param predicate
     * @param root
     * @param cb
     * @return
     */
    private List<Predicate> appendPredicateDate(String prop, String date, List<Predicate> predicate, Root<Feed> root, CriteriaBuilder cb) {
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = format.parse(date);
            endDate = startDate;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.add(Calendar.DATE, 1);
            endDate = calendar.getTime();
            calendar = null;
            predicate.add(cb.between(root.get(prop).as(Date.class), startDate, endDate));
        } catch (ParseException e) {

        }
        return predicate;
    }

}
