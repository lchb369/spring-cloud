package com.springcloud.lab.feedservice.schedule;

import com.springcloud.lab.feedservice.common.AmqpSender;
import com.springcloud.lab.feedservice.common.KafkaSender;
import com.springcloud.lab.feedservice.service.FeedSearchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lchb on 2017-9-14.
 */
@Component
public class TaskSchedule {

    @Autowired
    FeedSearchService feedSearchService;

    @Autowired
    AmqpSender amqpSender;

    @Autowired
    KafkaSender kafkaSender;

    //每60秒执行
    @Scheduled(cron = "*/10 * * * * * ")
    public void rabbitMQSend(){

        System.out.println("TaskSchedule runEveryOneMinites"+new Date());
        amqpSender.sendMsg("queue","xxxxxxxxxxxxxxxxxxxxxx");
    }

    @RabbitListener(queues="queue")    //监听器监听指定的Queue
    public void rabbitMQRecv(String str) {
        System.out.println("rabbitMQRecv:"+str);
    }


    //如果发送超时，一般是kafka监听ip问题，不支持外网
    @Scheduled(cron = "*/10 * * * * * ")
    public void kafkaSend(){

        //System.out.println("kafkaSend "+new Date());
        //kafkaSender.sendMessage("testTopic","kafka..............");
    }

    /*
    @KafkaListener(topics="testTopic")    //监听器监听指定的Queue
    public void kafkaRecv(String str) {
        System.out.println("kafkaRecv:"+str);
    }
    */

    @Scheduled(cron = "*/10 * * * * * ")
    public void test(){

        feedSearchService.crudTest();
    }



    //每隔多少秒
    @Scheduled(fixedRate=300000)
    public void timerRate() {
        System.out.println("TaskSchedule timerRate"+new Date());
    }

}
