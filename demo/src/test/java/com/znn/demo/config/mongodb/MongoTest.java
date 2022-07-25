package com.znn.demo.config.mongodb;

import com.znn.demo.config.servlet.TestCast;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author 周闹闹
 * @version 1.0
 */
@SpringBootTest
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    TestCast testCast;

    @Test
    public void save(){
        testCast.setId(1);
        mongoTemplate.save(testCast);
    }

    @Test
    public void find(){
        System.out.println(mongoTemplate.findAll(TestCast.class));
    }
}
