package com.znn.demo.dao;

import com.znn.demo.domain.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Mapper
public interface SysRoleDAO {

    List<SysRole> selectRolePermissionByUserId(Long userId);

    /**
     * 根据用户名查询角色
     *
     * @param userName 用户名
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserName(String userName);
}
