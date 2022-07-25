package com.znn.demo.dao;

import com.znn.demo.domain.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Mapper
public interface SysUserDAO {

    SysUser selectByUserName(String userName);

    int insertUser(SysUser sysUser);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SysUser user);

    List<SysUser> selectAll(@Param("start")Integer start, @Param("pageSize")Integer pageSize);

    Integer selectTotal();

    SysUser selectRoleByUserName(String userName);

    SysUser checkPhoneUnique(String phonenumber);

    SysUser checkEmailUnique(String email);

    int updateUserAvatar(@Param("userName")String userName, @Param("avatar")String avatar);
}
