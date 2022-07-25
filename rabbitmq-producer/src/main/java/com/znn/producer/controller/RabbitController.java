package com.znn.producer.controller;

import com.znn.producer.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 周闹闹
 * @version 1.0
 */
@RestController
@RequestMapping("/test")
public class RabbitController {

    @Autowired
    RabbitMQService rabbitMQService;

    @GetMapping("/{name}")
    public void setQ(@PathVariable String name){
        System.out.println(name + "开始进入队列");
        for (int i = 0; i < 100000; i++) {
            rabbitMQService.sendMsg(name + i);
        }

    }

    @GetMapping("/topic/{name}")
    public void setTopicMQ(@PathVariable String name) {
        System.out.println(name + "开始进入topic队列");
        for (int i = 0; i < 100000; i++) {
            rabbitMQService.sendMsgTopic("topic" + name + i, "test.ha.t");
        }
    }
}
