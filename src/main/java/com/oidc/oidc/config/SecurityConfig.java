package com.oidc.oidc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// 启用Web安全
@EnableWebSecurity
// 声明这是一个配置类
@Configuration
public class SecurityConfig {

    // 定义一个Bean，用于密码加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 返回BCryptPasswordEncoder实例，用于加密密码
        return new BCryptPasswordEncoder();
    }
}
