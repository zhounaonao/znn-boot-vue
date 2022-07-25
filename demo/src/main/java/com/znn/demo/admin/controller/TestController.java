package com.znn.demo.admin.controller;

import com.znn.demo.config.redis.MyRedis;
import com.znn.demo.domain.AjaxResult;
import com.znn.demo.dao.bo.SysUserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 周闹闹
 * @version 1.0
 */
@RestController
@RequestMapping
public class TestController {

    @Value("${users[1].name}")
    private String name;

    @Value("${server.port}")
    private String port;

    @Value("${tempDir}")
    private String tempDir;

    // 所有属性都可以取出来
    @Autowired
    private Environment env;

    @Autowired
    private MyRedis myRedis;

    @Autowired
    private SysUserBO sysUserBO;

//    @GetMapping("/test")
    public AjaxResult test(){
        System.out.println(name);
        System.out.println(port);
        System.out.println(tempDir);
        System.out.println(env.getProperty("users[1].name"));
        System.out.println(myRedis);
        return AjaxResult.success();
    }


    @GetMapping("/test/cache")
    public AjaxResult cache(){
        return AjaxResult.success(sysUserBO.selectByUserName("znn"));
    }
}
