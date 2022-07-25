package com.znn.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Configuration
public class RabbitMQConfig {

    public static final String RABBITMQ_DEMO_TOPIC = "rabbitmqDemoTopic";

    public static final String RABBITMQ_DEMO_DIRECT_EXCHANGE = "rabbitmqDemoDirectExchange";

    public static final String RABBITMQ_DEMO_DIRECT_ROUTING = "rabbitmqDemoDirectRouting";

    public static final String TOPIC_EXCHANGE_NAME = "test.topicExchange";

    public static final String QUEUE_NAME = "test.queue";


    /**
     * 交换机
     * @return
     */
    @Bean
    public Exchange exception() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 队列
     * @return
     */
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    /**
     * 队列2
     * @return
     */
    @Bean
    public Queue queue2() {
        return QueueBuilder.durable(QUEUE_NAME + 2).build();
    }

    /**
     * 绑定关系
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exception())
                .with("test.*.t").noargs();
    }

    /**
     * 绑定关系
     * @return
     */
    @Bean
    public Binding binding2() {
        return BindingBuilder
                .bind(queue2())
                .to(exception())
                .with("test.#").noargs();
    }
}
