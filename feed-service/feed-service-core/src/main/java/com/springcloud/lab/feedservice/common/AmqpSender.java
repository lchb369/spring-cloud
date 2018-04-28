package com.springcloud.lab.feedservice.common;

import com.springcloud.lab.feedservice.config.AmqpConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by lchb on 2017-9-15.
 */
@Component
public class AmqpSender{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String queue,String content) {

        rabbitTemplate.convertAndSend(queue,content);
        //CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //rabbitTemplate.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.ROUTINGKEY, content, correlationId);
    }

}
