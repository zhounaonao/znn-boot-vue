package com.znn.producer.service;

/**
 * @author 周闹闹
 * @version 1.0
 */
public interface RabbitMQService {

    public String sendMsg(String msg);

    String sendMsgTopic(String msg, String routingKey);
}
