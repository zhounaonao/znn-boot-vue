package com.znn.consumer.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Component
public class RabbitReceive {

    @RabbitListener(queues = "test.queue")
    public void receive(Map map) {
        System.out.println("队列1" + map.toString());
    }


    @RabbitListener(queues = "test.queue2")
    public void receive2(Map map){
        System.out.println("队列2" + map.toString());
    }

}
