package com.znn.demo.quartz.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 周闹闹
 * @version 1.0
 * 工作明细
 *
 * 触发器
 *
 * 调度器
 *
 * 复杂方式
 */
@Configuration
public class QuartzConfig {

    // 工作明细
    // 工作持久化storeDurably
    @Bean
    public JobDetail printJobDetail(){
        return JobBuilder
                .newJob(MyQuartz.class)
                // ...
                .storeDurably()
                .build();
    }

    // 触发器
//    @Bean
    public Trigger printJobTrigger(){
        // 秒 分 时 日 月 星期 -- 日和星期一般不一起定义
        ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        return TriggerBuilder.newTrigger()
                // ...
                .forJob(printJobDetail())
                .withSchedule(scheduleBuilder)
                .build();
    }

}
