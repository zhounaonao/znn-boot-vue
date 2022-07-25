package com.znn.demo.dao.bo.impl;

import com.znn.demo.common.constant.UserConstants;
import com.znn.demo.common.utils.text.StringUtils;
import com.znn.demo.dao.SysPostDAO;
import com.znn.demo.dao.SysRoleDAO;
import com.znn.demo.dao.SysUserDAO;
import com.znn.demo.dao.bo.SysUserBO;
import com.znn.demo.domain.entity.SysPost;
import com.znn.demo.domain.entity.SysRole;
import com.znn.demo.domain.entity.SysUser;
import com.znn.demo.framework.aspectj.annotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Service
public class SysUserBOImpl implements SysUserBO {

    @Autowired
    SysUserDAO sysUserDAO;

    @Autowired
    SysRoleDAO sysRoleDAO;

    @Autowired
    SysPostDAO sysPostDAO;

    // 将其保存在redis内存中使用cacheSpace::userName为键，之后每次改变的时候都应该将其的值改变
    @Cacheable(value = "cacheSpace", key = "#userName")
    @Override
    public SysUser selectByUserName(String userName) {
        SysUser sysUser = sysUserDAO.selectByUserName(userName);

        return sysUser;
    }

    @Override
    public int insertUser(SysUser sysUser) {
        return sysUserDAO.insertUser(sysUser);
    }

    @Override
    public int updateUserProfile(SysUser user) {
        return sysUserDAO.updateUser(user);
    }

    // 更新用户成功的时候，将该用户更新入缓存中,无论怎样，都将方法的返回值放到缓存中。
    @CachePut(value = "cacheSpace", key = "#userName")
    @Override
    public SysUser updateCache(String userName) {
        // 当前采用了为空则不更新，所以在更新缓存的时候需要去数据库重新查询一遍
        return sysUserDAO.selectByUserName(userName);
    }

    @CacheEvict("cacheSpace")
    @Log(title = "删除用户缓存")
    @Override
    public void deleteCache(String userName) {

    }


    @Override
    public SysUser selectRoleByUserName(String userName) {
        return sysUserDAO.selectRoleByUserName(userName);
    }

    @Override
    public List<SysUser> selectAll(Integer pageNum, Integer pageSize) {
        return sysUserDAO.selectAll((pageNum-1) * pageSize, pageSize);
    }

    @Override
    public Integer selectTotal() {
        return sysUserDAO.selectTotal();
    }

    /**
     * 根据用户名查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> sysRoles = sysRoleDAO.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(sysRoles)) {
            return StringUtils.EMPTY;
        }
        return sysRoles.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 根据用户名查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> sysPosts = sysPostDAO.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(sysPosts)) {
            return StringUtils.EMPTY;
        }
        return sysPosts.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId())? -1L: user.getUserId();
        SysUser info = sysUserDAO.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = sysUserDAO.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return sysUserDAO.updateUserAvatar(userName, avatar) > 0;
    }
}
