package com.znn.demo.quartz.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Component
public class ScheduledBean {

    @Scheduled(cron = "0/1 * * * * ?")
    public void printLog(){
        System.out.println("spring task run...");
    }
}
