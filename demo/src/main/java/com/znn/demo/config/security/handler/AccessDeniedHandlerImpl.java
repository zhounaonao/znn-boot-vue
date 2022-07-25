package com.znn.demo.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.znn.demo.domain.AjaxResult;
import com.znn.demo.common.utils.security.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    // 当授权时出现异常，捕获并返回异常json
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        // 处理异常
        AjaxResult error = AjaxResult.error(HttpStatus.FORBIDDEN.value(), "您的权限不足");
        String json = JSON.toJSONString(error);
        WebUtils.renderString(httpServletResponse, json);

    }
}
