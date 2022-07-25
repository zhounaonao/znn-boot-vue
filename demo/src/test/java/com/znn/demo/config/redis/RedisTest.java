package com.znn.demo.config.redis;

import com.znn.demo.common.core.redis.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * @author 周闹闹
 * @version 1.0
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    RedisCache redisCache;

    @Test
    public void setCode(){
        String code = "112232";
        redisCache.setCacheObject("tele:13028902352", code, 2, TimeUnit.MINUTES);

    }

    @Test
    public void getCode(){
        String s = redisCache.<String>getCacheObject("tele:13028902352");
        System.out.println(s);
    }

    @Autowired
    public RedisTemplate redisTemplate; // 以对象为操作

    // StringRedisTemplate继承了RedisTemplate<String, String>
    @Autowired
    public StringRedisTemplate stringRedisTemplate; // 以字符串为操作

    @Test
    void set(){
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("age", 14);
    }

    @Test
    void get(){
        ValueOperations ops = redisTemplate.opsForValue();
        Object age = ops.get("age");
        System.out.println(age);
    }

    @Test
    void hset(){
        HashOperations ops = stringRedisTemplate.opsForHash();
        ops.put("info", "name", "znn");
    }

    @Test
    void hget(){
        HashOperations ops = stringRedisTemplate.opsForHash();
        Object name = ops.get("info", "name");
        System.out.println(name);
    }


}
