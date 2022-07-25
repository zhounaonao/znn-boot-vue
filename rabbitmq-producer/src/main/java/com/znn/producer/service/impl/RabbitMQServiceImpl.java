package com.znn.producer.service.impl;

import cn.hutool.core.lang.UUID;
import com.znn.producer.config.RabbitMQConfig;
import com.znn.producer.service.RabbitMQService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.znn.producer.config.RabbitMQConfig.TOPIC_EXCHANGE_NAME;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Service
public class RabbitMQServiceImpl implements RabbitMQService {

    //日期格式化
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public String sendMsg(String msg) {

        try {
            String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
            String sendTime = sdf.format(new Date());
            Map<String, Object> map = new HashMap<>();
            map.put("msgId", msgId);
            map.put("sendTime", sendTime);
            map.put("msg", msg);
            rabbitTemplate.convertAndSend(RabbitMQConfig.RABBITMQ_DEMO_DIRECT_EXCHANGE, RabbitMQConfig.RABBITMQ_DEMO_DIRECT_ROUTING, map);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    @Override
    public String sendMsgTopic(String msg, String routingKey) {

        try {
            String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
            String sendTime = sdf.format(new Date());
            Map<String, Object> map = new HashMap<>();
            map.put("msgId", msgId);
            map.put("sendTime", sendTime);
            map.put("msg", msg);
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, routingKey, map);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }
}
