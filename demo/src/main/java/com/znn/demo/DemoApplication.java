package com.znn.demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.znn.demo.config.MainConfig;
import com.znn.demo.config.redis.MyRedis;
import com.znn.demo.config.servlet.ServletConfig;
import com.znn.demo.config.servlet.TestCast;
import com.znn.demo.dao.SysUserDAO;
import com.znn.demo.framework.aspectj.annotation.EnableLogging;
import com.znn.demo.framework.aspectj.aop.LogAspectj;
import com.znn.producer.controller.RabbitController;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;


/**
 * @author 周闹闹
 * @version 1.0
 * 1.先实现controller输出 web
 * 2.完成登录接口 SpringSecurity
 *
 *
 */
@SpringBootApplication
// 管理配置属性的类，看的清晰一点（并且将管理的类变成bean）
@EnableConfigurationProperties({MainConfig.class, ServletConfig.class})
@EnableCaching
// 开启定时任务
//@EnableScheduling

// 开启日志开关，自定义
@EnableLogging
@ComponentScan(value = {"com.znn.demo", "com.znn.producer"})
public class DemoApplication {

//    @Bean
////    prefix 只能使用纯小写字母、数字、下划线作为合法字符
//    @ConfigurationProperties(prefix = "datasources")// 为第三方bean绑定属性
//    public DruidDataSource druidDataSource(){
//        DruidDataSource druidDataSource = new DruidDataSource();
////        druidDataSource.setDriverClassName("com.cj.driver");
//        return druidDataSource;
//    }

    public static void main(String[] args) {
        // 设置高优先级的配置信息
//        System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext run = new SpringApplication().run(DemoApplication.class, args);
        System.out.println("启动成功");
        RabbitController bean = run.getBean(RabbitController.class);
        System.out.println(bean);
        // 获取所有bean
        String[] beanDefinitionNames = run.getBeanDefinitionNames();



    }
}
