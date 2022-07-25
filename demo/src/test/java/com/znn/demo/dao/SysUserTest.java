package com.znn.demo.dao;

import com.znn.demo.dao.bo.SysUserBO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 周闹闹
 * @version 1.0
 */
@SpringBootTest
public class SysUserTest {

    @Autowired
    SysUserBO sysUserBO;

    @Test
    public void test1(){
        System.out.println(sysUserBO.selectByUserName("znn"));
    }

    @Test
    public void test2(){
        System.out.println(sysUserBO.selectRoleByUserName("znn"));
    }
}
