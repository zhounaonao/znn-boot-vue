package com.znn.demo.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
@SpringBootTest
public class SysMenuTest {

    @Autowired
    SysMenuDAO sysMenuDAO;

    @Autowired
    SysUserDAO sysUserDAO;

    @Test
    public void test1(){
        List<String> perms = sysMenuDAO.selectMenuPermsByUserId(1L);
        System.out.println(perms);
    }
}
