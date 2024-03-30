package com.oidc.oidc;

import com.oidc.oidc.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class OidcApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() {
        Integer maxId = userMapper.findMaxId();
        System.out.println("The maximum id in the user table is: " + maxId);
    }

}
