package com.springcloud.lab.feedservice.service;

import com.springcloud.lab.api.dto.params.FeedQueryRequest;
import com.springcloud.lab.feedservice.dao.elasticsearch.FeedDocRepository;
import com.springcloud.lab.feedservice.dao.elasticsearch.docs.FeedDoc;
import org.apache.lucene.queryparser.xml.builders.TermsFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 参考文档:
 * 1.Elasticsearch: 权威指南
 * https://www.elastic.co/guide/cn/elasticsearch/guide/current/index.html
 * 2.
 */
@Service
public class FeedSearchService {

    @Autowired
    private FeedDocRepository feedDocRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    private int random(int min,int max){
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

    /**
     * ES 基本增删改查测试
     * schedule TaskSchedule中定时调用
     */
    public void crudTest(){

        //删除全部记录
        //feedDocRepository.deleteAll();
        //删除id=?的记录
        //feedDocRepository.delete(1);

        List<String> titleList=getTitle();
        List<String> contentList=getContent();

        for(int i=1;i<1000;i++){
            int index=i%titleList.size();

            FeedDoc feedDoc=new FeedDoc();
            feedDoc.setId(i);
            feedDoc.setTitle(titleList.get(index));
            feedDoc.setAuthor("作者"+random(1,20));
            feedDoc.setContent(contentList.get(index));
            feedDoc.setType(random(1,10));

            //保存，如果ID存在，则更新，否则新增
            feedDocRepository.save(feedDoc);
        }

        //根据ID查询
        FeedDoc feedDoc=feedDocRepository.findOne(1);
        System.out.println("===="+feedDoc.getTitle());

        //更新字段
        feedDoc.setTitle("test title 22222");
        feedDocRepository.save(feedDoc);

        //自定义查询
        List<FeedDoc> feedDocList=feedDocRepository.findByTitleLike("title");

    }

    /**
     * 原生查询，复杂查询
     * @param requestVo
     * @return
     * 测试:http://127.0.0.1:8023/feed/v1/search
     */
    public List<FeedDoc> search(FeedQueryRequest requestVo){

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        //构造查询条件
        //title中所有包括"沙"的记录
        queryBuilder.must(  QueryBuilders.termQuery("title", "沙"));

        //在must的基础上，按author中包括"10" 排序，匹配度高的排前面
        queryBuilder.should(QueryBuilders.termQuery("author","10"));

        //构造查询条件
        BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();
        filterBuilder.must(  QueryBuilders.termQuery("title", "沙"));
        filterBuilder.should(QueryBuilders.termQuery("author","10"));



        //should在filter中无用,相当于must
        //queryBuilder.must(QueryBuilders.matchAllQuery()).filter(filterBuilder);


        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withIndices("feed_index").build();

        Long count=elasticsearchTemplate.count(searchQuery);
        List<FeedDoc> feedDocList=elasticsearchTemplate.queryForList(searchQuery,FeedDoc.class);
        System.out.println("match record"+feedDocList.size()+"count===="+count);

        return feedDocList;
    }


    private List<String> getTitle() {
        List<String> list = new ArrayList<>();
        list.add("《如梦令·常记溪亭日暮》");
        list.add("《醉花阴·薄雾浓云愁永昼》");
        list.add("《声声慢·寻寻觅觅》");
        list.add("《永遇乐·落日熔金》");
        list.add("《如梦令·昨夜雨疏风骤》");
        list.add("《渔家傲·雪里已知春信至》");
        list.add("《点绛唇·蹴[1]罢秋千》");
        list.add("《点绛唇·寂寞深闺》");
        list.add("《蝶恋花·泪湿罗衣脂粉满》");
        list.add("《蝶恋花 离情》");
        list.add("《浣溪沙》");
        list.add("《浣溪沙》");
        list.add("《浣溪沙》");
        list.add("《浣溪沙》");
        list.add("《浣溪沙》");
        list.add("《减字木兰花·卖花担上》");
        list.add("《临江仙·欧阳公作《蝶恋花》");
        list.add("《临江仙·庭院深深深几许》");
        list.add("《念奴娇·萧条庭院》");
        list.add("《菩萨蛮·风柔日薄春犹早》");
        list.add("《菩萨蛮·归鸿声断残云碧》");
        list.add("《武陵春·风住尘香花已尽》");
        list.add("《一剪梅·红藕香残玉蕈秋》");
        list.add("《渔家傲·天接云涛连晓雾》");
        list.add("《鹧鸪天·暗淡轻黄体性柔》");
        list.add("《鹧鸪天·寒日萧萧上锁窗》");
        list.add("《一剪梅·红藕香残玉簟秋》");
        list.add("《如梦令·常记溪亭日暮》");
        list.add("《浣溪沙》");
        list.add("《浣溪沙》");
        list.add("《浣溪沙》");
        list.add("《蝶恋花·泪湿罗衣脂粉满》");
        list.add("《蝶恋花·暖日晴风初破冻》");
        list.add("《鹧鸪天·寒日萧萧上锁窗》");
        list.add("《醉花阴·薄雾浓云愁永昼》");
        list.add("《鹧鸪天·暗淡轻黄体性柔》");
        list.add("《蝶恋花·永夜恹恹欢意少》");
        list.add("《浣溪沙》");
        list.add("《浣溪沙》");
        list.add("《如梦令·谁伴明窗独坐》");
        return list;
    }

    private List<String> getContent() {
        List<String> list = new ArrayList<String>();
        list.add("初中 宋·李清照 常记溪亭日暮，沉醉不知归路，兴尽晚回舟，误入藕花深处。争渡，争渡");
        list.add("重阳节 宋·李清照 薄雾浓云愁永昼，瑞脑消金兽。佳节又重阳，玉枕纱厨，半夜凉初透。东");
        list.add("闺怨诗 宋·李清照 寻寻觅觅，冷冷清清，凄凄惨惨戚戚。乍暖还寒时候，最难将息。三杯两");
        list.add("元宵节 宋·李清照 落日熔金，暮云合璧，人在何处。染柳烟浓，吹梅笛怨，春意知几许。元");
        list.add("婉约诗 宋·李清照 昨夜雨疏风骤，浓睡不消残酒，试问卷帘人，却道海棠依旧。知否，知否");
        list.add("描写梅花 宋·李清照 雪里已知春信至，寒梅点缀琼枝腻，香脸半开娇旖旎，当庭际，玉人浴出");
        list.add(" 宋·李清照 蹴罢秋千，起来慵整纤纤手。露浓花瘦，薄汗轻衣透。见客入来，袜刬金");
        list.add("闺怨诗 宋·李清照 寂寞深闺，柔肠一寸愁千缕。惜春春去。几点催花雨。倚遍阑干，只是无");
        list.add("婉约诗 宋·李清照 泪湿罗衣脂粉满。四叠阳关，唱到千千遍。人道山长水又断。萧萧微雨闻");
        list.add("描写春天 宋·李清照 暖雨晴风初破冻，柳眼梅腮，已觉春心动。酒意诗情谁与共？泪融残粉花");
        list.add("寒食节 宋·李清照 淡荡春光寒食天，玉炉沈水袅残烟，梦回山枕隐花钿。海燕未来人斗草，");
        list.add(" 宋·李清照 髻子伤春慵更梳，晚风庭院落梅初，淡云来往月疏疏，玉鸭薰炉闲瑞脑，");
        list.add(" 宋·李清照 莫许杯深琥珀浓，未成沉醉意先融。疏钟已应晚来风。瑞脑香消魂梦断，");
        list.add("闺怨诗 宋·李清照 小院闲窗春已深，重帘未卷影沉沉。倚楼无语理瑶琴，远岫出山催薄暮。");
        list.add("爱情诗 宋·李清照 绣幕芙蓉一笑开，斜偎宝鸭亲香腮，眼波才动被人猜。一面风情深有韵，");
        list.add("描写春天 宋·李清照 卖花担上，买得一枝春欲放。泪染轻匀，犹带彤霞晓露痕。怕郎猜道，奴");
        list.add("》 宋·李清照 欧阳公作《蝶恋花》，有“深深深几许”之句，予酷爱之。用其语作“庭");
        list.add("描写梅花 宋·李清照 庭院深深深几许，云窗雾阁春迟，为谁憔悴损芳姿。夜来清梦好，应是发");
        list.add("寒食节 宋·李清照 萧条庭院，又斜风细雨，重门须闭。宠柳娇花寒食近，种种恼人天气。险");
        list.add("思乡诗 宋·李清照 风柔日薄春犹早，夹衫乍著心情好。睡起觉微寒，梅花鬓上残。故乡何处");
        list.add("描写春天 宋·李清照 归鸿声断残云碧，背窗雪落炉烟直。烛底凤钗明，钗头人胜轻。角声催晓");
        list.add("闺怨诗 宋·李清照 风住尘香花已尽，日晚倦梳头。物是人非事事休，欲语泪先流。闻说双溪");
        list.add(" 宋·李清照 红藕香残玉蕈秋，轻解罗裳，独上兰舟。云中谁寄锦书来？雁字回时，月");
        list.add("豪放诗 宋·李清照 天接云涛连晓雾。星河欲转千帆舞。仿佛梦魂归帝所。闻天语。殷勤问我");
        list.add("描写花 宋·李清照 暗淡轻黄体性柔。情疏迹远只香留。何须浅碧深红色，自是花中第一流。");
        list.add("描写秋天 宋·李清照 寒日萧萧上琐窗，梧桐应恨夜来霜。酒阑更喜团茶苦，梦断偏宜瑞脑香。");
        list.add("闺怨诗 宋·李清照 红藕香残玉簟秋。轻解罗裳，独上兰舟。云中谁寄锦书来？雁字回时，月");
        list.add(" 宋·李清照 常记溪亭日暮。沈醉不知归路。兴尽晚回舟，误入藕花深处。争渡。争渡");
        list.add(" 宋·李清照 莫许杯深琥珀浓。未成沈醉意先融。已应晚来风。瑞脑香消魂梦断，");
        list.add(" 宋·李清照 小院闲窗春色深。重帘未卷影沈沈。倚楼无语理瑶琴。远岫出山催薄暮，");
        list.add(" 宋·李清照 淡荡春光寒食天。玉炉沈水袅残烟。梦回山枕隐花钿。海燕未来人斗草，");
        list.add(" 宋·李清照 泪湿罗衣脂粉满。四叠阳关，唱到千千遍。人道山长山又断。萧萧微雨闻");
        list.add(" 宋·李清照 暖日晴风初破冻。柳眼眉腮，已觉春心动。酒意诗情谁与共。泪融残粉花");
        list.add(" 宋·李清照 寒日萧萧上锁窗。梧桐应恨夜来霜。酒阑更喜团茶苦，梦断偏宜瑞脑香。");
        list.add(" 宋·李清照 薄雾浓云愁永昼。瑞脑消金兽。佳节又重阳，玉枕纱厨，半夜凉初透。东");
        list.add(" 宋·李清照 暗淡轻黄体性柔。情疏迹远只香留。何须浅碧深红色，自是花中第一流。");
        list.add(" 宋·李清照 永夜恹恹欢意少。空梦长安，认取长安道。为报今年春色好。花光月影宜");
        list.add(" 宋·李清照 髻子伤春慵更梳。晚风庭院落梅初。淡云来往月疏疏。玉鸭熏炉闲瑞脑，");
        list.add(" 宋·李清照 绣面芙蓉一笑开。斜飞宝鸭衬香腮。眼波才动被人猜。一面风情深有韵，");
        list.add(" 宋·李清照 谁伴明窗独坐，我共影儿俩个。灯尽欲眠时，影也把人抛躲。无那，无那");
        return list;
    }

}
