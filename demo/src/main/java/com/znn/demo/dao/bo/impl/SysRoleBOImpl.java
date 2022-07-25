package com.znn.demo.dao.bo.impl;

import com.znn.demo.dao.SysRoleDAO;
import com.znn.demo.dao.bo.SysRoleBO;
import com.znn.demo.domain.entity.SysRole;
import com.znn.demo.domain.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Service
public class SysRoleBOImpl implements SysRoleBO {

    @Autowired
    SysRoleDAO sysRoleDAO;

    @Override
    public List<String> selectRolePermissionByUserId(SysUser sysUser) {
        List<String> roles = new ArrayList<>();
        if (sysUser.isAdmin()){
            roles.add("admin");
            return roles;
        }
        List<SysRole> sysRoles = sysRoleDAO.selectRolePermissionByUserId(sysUser.getUserId());
        roles = sysRoles.stream()
                .flatMap(sysRole -> Arrays.stream(sysRole.getRoleKey().trim().split(",")))
                .collect(Collectors.toList());
        return roles;
    }
}
