package com.znn.producer.service;

import com.znn.producer.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ÷‹ƒ÷ƒ÷
 * @version 1.0
 */
@SpringBootTest
public class RabbitMQTest {

    @Autowired
    RabbitMQService rabbitMQService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.port}")
    String port;

    @Test
    public void rabbit1(){
//        rabbitMQService.sendMsg(port);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "test.aa", "cc");

    }

}
