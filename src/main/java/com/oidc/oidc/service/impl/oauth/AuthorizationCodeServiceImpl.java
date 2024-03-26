package com.oidc.oidc.service.impl.oauth;

import com.oidc.oidc.mapper.AuthorizationCodeMapper;
import com.oidc.oidc.pojo.AuthorizationCode;
import com.oidc.oidc.service.interfaces.oauth.AuthorizationCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public AuthorizationCodeServiceImpl(AuthorizationCodeMapper authorizationCodeMapper) {
        this.authorizationCodeMapper = authorizationCodeMapper;
    }

    @Override
    public ResponseEntity<?> createAuthorizationCode(Map<String,String> map) {
        Map<String, Object> responseBody=new HashMap<>();
        Boolean ifNickName= "true".equals(map.get("userNickName"));
        Boolean ifEmail="true".equals(map.get("userEmail"));
        Boolean ifAvatar="true".equals(map.get("userAvatar"));
        Boolean ifIntroduction="true".equals(map.get("userIntroduction"));

        String codeWord = UUID.randomUUID().toString();

        Integer id = authorizationCodeMapper.findMaxId();
        if (id == null) {
            id = 0;
        }
        id++;

        AuthorizationCode authorizationCode=new AuthorizationCode(id,codeWord,ifNickName,ifEmail,ifAvatar,ifIntroduction);

        authorizationCodeMapper.insert(authorizationCode);
        responseBody.put("authorizationCode", authorizationCode.getCodeWord());
        return ResponseEntity.ok(responseBody);

    }

    @Override
    public String validateAndGetScope(String code) {
        return null;
    }
}
