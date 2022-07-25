package com.znn.demo.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.znn.demo.domain.AjaxResult;
import com.znn.demo.common.utils.security.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    // 当认证时段出现异常，则会捕获异常，返回异常json
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        // 处理异常,401登录失效，重新登录
        AjaxResult error = AjaxResult.error(HttpStatus.UNAUTHORIZED.value(), "用户认证失败，请重新登录");

        String json = JSON.toJSONString(error);
        WebUtils.renderString(httpServletResponse, json);

    }
}
