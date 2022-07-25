package com.znn.demo.service;

import com.znn.demo.domain.AjaxResult;
import com.znn.demo.domain.entity.SysUser;
import com.znn.demo.domain.model.LoginBody;

/**
 * @author 周闹闹
 * @version 1.0
 */
public interface LoginService {

    AjaxResult login(LoginBody loginBody);

    AjaxResult register(SysUser sysUser);

    AjaxResult logout();
}
