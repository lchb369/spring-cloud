package com.springcloud.lab.searchservice.common;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
