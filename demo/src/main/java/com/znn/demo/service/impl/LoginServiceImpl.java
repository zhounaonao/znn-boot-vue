package com.znn.demo.service.impl;

import com.znn.demo.common.exception.user.CaptchaException;
import com.znn.demo.common.exception.user.CaptchaExpireException;
import com.znn.demo.common.exception.user.UserPasswordNotMatchException;
import com.znn.demo.common.constant.Constants;
import com.znn.demo.common.utils.DateUtils;
import com.znn.demo.common.utils.ServletUtils;
import com.znn.demo.common.utils.ip.IpUtils;
import com.znn.demo.domain.AjaxResult;
import com.znn.demo.domain.entity.SysRole;
import com.znn.demo.domain.entity.SysUser;
import com.znn.demo.domain.model.LoginBody;
import com.znn.demo.domain.model.LoginUser;
import com.znn.demo.service.ConfigService;
import com.znn.demo.service.LoginService;
import com.znn.demo.common.core.redis.RedisCache;
import com.znn.demo.common.utils.text.StringUtils;
import com.znn.demo.service.SendMailService;
import com.znn.demo.dao.bo.SysUserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Autowired
    SendMailService sendMailService;

    @Autowired
    ConfigService configService;

    @Autowired
    SysUserBO sysUserBO;

    @Autowired
    TokenService tokenService;

    @Override
    public AjaxResult login(LoginBody loginBody) {
        AjaxResult ajaxResult = AjaxResult.success();
        // 验证码开关
        boolean captchaOnOff = configService.selectCaptchaOnOff();
        // 验证code
        if (captchaOnOff) {
            validateCaptcha(loginBody);
        }
        Authentication authenticate = null;

        try {
            // 1. UsernamePasswordAuthenticationToken创建 Authentication对象
            // 传入 AuthenticationManager authenticate 用户认证
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword());
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            // 用户认证未通过

            throw new UserPasswordNotMatchException();
        }
        // 认证没通过，给出提示
        if (StringUtils.isNull(authenticate)) {
            throw new RuntimeException("登陆失败");
        }
        // 认证通过，使用userid生成一个jwt jwt存入 ResponseResult 返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getSysUser().getUserId().toString();
        // 记录登录信息
        recordLoginInfo(loginUser.getSysUser().getUserId());
//        String jwt = JwtUtil.createJWT(userId);
        String token = tokenService.createToken(loginUser);
        // map=token:jwt
        // 将角色传给前端
        SysUser sysUserRole = sysUserBO.selectRoleByUserName(loginUser.getUsername());
        ajaxResult.put("token", token);
        if (Objects.isNull(sysUserRole)) {
            ArrayList<SysRole> roles = new ArrayList<>(1);
            roles.add(new SysRole(2L));
            ajaxResult.put("role", roles);
        } else {
            ajaxResult.put("role", sysUserRole.getRoles());
        }
        ajaxResult.put("userName", loginUser.getUsername());
        ajaxResult.put("loginUser", loginUser);
//        userMap.put("perms", JSON.toJSONString(loginUser.getPerms()));
        // 把完整的用户信息存入redis userid作为key
//        redisCache.setCacheObject("token:" + userId, token);
//        redisCache.setCacheObject("login:" + userId, loginUser);
        return ajaxResult;
    }

    /**
     * 校验验证码
     *
     * @param loginBody 登录对象
     * @return 结果
     */
    private void validateCaptcha(LoginBody loginBody) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + StringUtils.nvl(loginBody.getUuid(), "");
        // 从redis取出验证码
        String captcha = redisCache.getCacheObject(verifyKey);
        // redis没有储存验证码，或给到的uuid错误，或者验证码到期
        if (captcha == null) {
            // 记录

            throw new CaptchaExpireException();
        }
        // 验证验证码
        if (!loginBody.getCode().equals(captcha)) {
            // 记录

            throw new CaptchaException();
        }

    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        sysUserBO.updateUserProfile(sysUser);
    }

    @Override
    public AjaxResult register(SysUser sysUser) {
        // 注册
        // 1.加密后写入数据库
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(encodePassword);
        sysUserBO.insertUser(sysUser);

        // 2.发送邮件
        String context = "<h1>欢迎加入</h1>" +
                "<a href='https://www.baidu.com'>点击验证你的邮箱</a>";
        sendMailService.sendMail(sysUser.getEmail(), "注册验证你的邮件for(znn)", context);

        return AjaxResult.success();
    }

    @Override
    public AjaxResult logout() {
        // 获取 SecurityContextHolder 中的用户id
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken)
                        SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getSysUser().getUserId();
        // 删除redis中的值
        redisCache.deleteObject("token:" + userId);
        redisCache.deleteObject("login:" + userId);

        return AjaxResult.success();
    }
}
