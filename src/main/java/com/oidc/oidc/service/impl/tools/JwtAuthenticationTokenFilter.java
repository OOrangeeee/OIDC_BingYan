package com.oidc.oidc.service.impl.tools;

import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.User;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 晋晨曦
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final UserMapper userMapper;

    public JwtAuthenticationTokenFilter(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取token
        String token = request.getHeader("Authorization");

        // 如果token为空或者不是以Bearer开头，则直接放行
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 去掉Bearer
        token = token.substring(7);

        // 从token中解析出用户id
        String userid;
        try {
            Claims claims = JwtTool.parseJwt(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 根据用户id查询用户信息
        User user = userMapper.selectById(Integer.parseInt(userid));

        // 如果用户信息为空，则抛出异常
        if (user == null) {
            throw new RuntimeException("用户名未登录");
        }

        // 封装用户信息
        UserDetailImpl loginUser = new UserDetailImpl(user);

        // 设置认证信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);

        // 设置上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
