package com.oidc.oidc.service.impl.oauth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.AuthorizationCodeMapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.AuthorizationCode;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.interfaces.oauth.AuthorizationCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author 晋晨曦
 */
@Service
public class AuthorizationCodeServiceImpl implements AuthorizationCodeService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationCodeServiceImpl.class);

    private final AuthorizationCodeMapper authorizationCodeMapper;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public AuthorizationCodeServiceImpl(AuthorizationCodeMapper authorizationCodeMapper, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.authorizationCodeMapper = authorizationCodeMapper;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> createAuthorizationCode(Map<String, String> map) {
        Map<String, Object> responseBody = new HashMap<>();
        Boolean ifNickName = "true".equals(map.get("userNickname"));
        Boolean ifEmail = "true".equals(map.get("userEmail"));
        Boolean ifAvatar = "true".equals(map.get("userAvatar"));
        Boolean ifIntroduction = "true".equals(map.get("userIntroduction"));
        String userName = map.get("userName");
        String userPassword = map.get("userPassword");
        String state = map.get("state");

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);

        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            responseBody.put("error_message", "用户不存在");
            return ResponseEntity.badRequest().body(responseBody);
        }

        if (!passwordEncoder.matches(userPassword, user.getUserPassword())) {
            responseBody.put("error_message", "用户密码错误");
            return ResponseEntity.badRequest().body(responseBody);
        }

        String codeWord = UUID.randomUUID().toString();

        Integer id = authorizationCodeMapper.findMaxId();
        if (id == null) {
            id = 0;
        }
        id++;

        AuthorizationCode authorizationCode = new AuthorizationCode(id, codeWord, ifNickName, ifEmail, ifAvatar, ifIntroduction, user.getId());

        authorizationCodeMapper.insert(authorizationCode);
        responseBody.put("error_message", "授权码获取成功");
        responseBody.put("state", state);
        responseBody.put("authorizationCode", authorizationCode.getCodeWord());
        return ResponseEntity.ok(responseBody);

    }

    @Override
    public String validateAndGetScope(String code) {
        return null;
    }
}
