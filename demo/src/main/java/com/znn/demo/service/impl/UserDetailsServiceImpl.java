package com.znn.demo.service.impl;

import com.znn.demo.common.enums.UserStatus;
import com.znn.demo.common.exception.ServiceException;
import com.znn.demo.common.utils.text.StringUtils;
import com.znn.demo.dao.bo.SysMenuBO;
import com.znn.demo.dao.bo.SysUserBO;
import com.znn.demo.domain.entity.SysUser;
import com.znn.demo.domain.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    SysUserBO sysUserBO;

    @Autowired
    SysMenuBO sysMenuBO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserBO.selectByUserName(username);

        if (StringUtils.isNull(sysUser)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        } else if (UserStatus.DELETED.getCode().equals(sysUser.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        } else if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }

        // 把数据封装成UserDetails返回
        return new LoginUser(sysUser.getUserId(), sysUser.getDeptId(), sysUser, sysMenuBO.getPerms(sysUser));
    }
}
