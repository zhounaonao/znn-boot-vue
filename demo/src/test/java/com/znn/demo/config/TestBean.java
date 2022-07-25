package com.znn.demo.config;

import org.springframework.context.annotation.Bean;

/**
 * @author 周闹闹
 * @version 1.0
 */

public class TestBean {

    // 加载测试bean
    @Bean
    public String msg(){
        return "bean msg";
    }
}
