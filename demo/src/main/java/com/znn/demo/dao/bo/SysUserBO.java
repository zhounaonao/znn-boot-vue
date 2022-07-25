package com.znn.demo.dao.bo;

import com.znn.demo.domain.entity.SysUser;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
public interface SysUserBO {

    SysUser selectByUserName(String userName);

    int insertUser(SysUser sysUser);

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUserProfile(SysUser user);

    /**
     * 将用户更新入缓存中
     *
     * @param userName 用户名
     * @return 结果
     */
    SysUser updateCache(String userName);

    /**
     * 将用户从缓存中删除
     *
     * @param userName 用户信息
     * @return 结果
     */
    void deleteCache(String userName);

    SysUser selectRoleByUserName(String userName);

    List<SysUser> selectAll(Integer pageNum, Integer pageSize);

    Integer selectTotal();

    /**
     * 根据用户名查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    String selectUserRoleGroup(String userName);

    /**
     * 根据用户名查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    String selectUserPostGroup(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    String checkPhoneUnique(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    String checkEmailUnique(SysUser user);

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    boolean updateUserAvatar(String userName, String avatar);
}
