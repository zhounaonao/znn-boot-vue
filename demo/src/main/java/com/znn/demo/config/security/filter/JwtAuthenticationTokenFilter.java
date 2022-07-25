package com.znn.demo.config.security.filter;

import com.znn.demo.common.utils.security.SecurityUtils;
import com.znn.demo.domain.model.LoginUser;
import com.znn.demo.common.utils.security.JwtUtil;
import com.znn.demo.common.core.redis.RedisCache;
import com.znn.demo.common.utils.text.StringUtils;
import com.znn.demo.service.impl.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    RedisCache redisCache;

    @Autowired
    TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }

    //    @Override
    protected void doFilterInternal1(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 前端在请求头中加入token 获取token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token) || "null".equals(token)) {
            filterChain.doFilter(request, response); // 放行
            return;
        }
        // 解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace(); // token过期，或者token非法
            filterChain.doFilter(request, response);
            return;
        }
        // 从redis中获取token
        String redisToken = redisCache.getCacheObject("token:" + userId);
        if (!token.equals(redisToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        if (StringUtils.isNull(loginUser)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 能访问到这里的才是携带token访问成功
        // 存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}
