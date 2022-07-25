package com.znn.demo;

import com.znn.demo.config.TestBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * @author 周闹闹
 * @version 1.0
 */
@SpringBootTest
@Import(TestBean.class) // 加载测试范围应用，小范围测试
public class ConfigurationBean {

    @Autowired
    String msg;

    @Test
    public void soBean(){
        System.out.println(msg);
    }
}
