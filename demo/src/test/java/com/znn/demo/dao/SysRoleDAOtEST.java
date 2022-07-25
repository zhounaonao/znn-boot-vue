package com.znn.demo.dao;

import com.znn.demo.dao.bo.SysRoleBO;
import com.znn.demo.domain.entity.SysRole;
import com.znn.demo.domain.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
@SpringBootTest
public class SysRoleDAOtEST {

    @Autowired
    SysRoleDAO sysRoleDAO;

    @Autowired
    SysRoleBO sysRoleBO;

    @Autowired
    SysUserDAO sysUserDAO;

    @Test
    public void test1(){
        List<SysRole> sysRoles = sysRoleDAO.selectRolePermissionByUserId(1L);
        System.out.println(sysRoles);

    }

    @Test
    public void test2(){
        SysUser znn = sysUserDAO.selectByUserName("test");
        List<String> strings = sysRoleBO.selectRolePermissionByUserId(znn);
        System.out.println(strings);
    }

    @Test
    public void test3(){
        List<SysRole> sysRoles = sysRoleDAO.selectRolesByUserName("znn");
        System.out.println(sysRoles);
    }
}
