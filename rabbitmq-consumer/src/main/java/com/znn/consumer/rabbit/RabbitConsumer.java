package com.znn.consumer.rabbit;

import com.znn.common.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Component
@RabbitListener(queues = {RabbitMQConfig.RABBITMQ_DEMO_TOPIC})
public class RabbitConsumer {

    @RabbitHandler
    public void process(Map map){
        System.out.println(map);
    }

}
