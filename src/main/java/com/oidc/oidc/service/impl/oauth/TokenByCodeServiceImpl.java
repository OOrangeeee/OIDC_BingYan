package com.oidc.oidc.service.impl.oauth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.AuthorizationCodeMapper;
import com.oidc.oidc.pojo.AuthorizationCode;
import com.oidc.oidc.service.impl.tools.JwtTool;
import com.oidc.oidc.service.interfaces.oauth.TokenByCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class TokenByCodeServiceImpl implements TokenByCodeService {
    private final AuthorizationCodeMapper authorizationCodeMapper;

    public TokenByCodeServiceImpl(AuthorizationCodeMapper authorizationCodeMapper) {
        this.authorizationCodeMapper = authorizationCodeMapper;
    }

    @Override
    public ResponseEntity<?> generateTokens(String code, String state) {
        Map<String, Object> responseBody = new HashMap<>();
        QueryWrapper<AuthorizationCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code_word", code);

        AuthorizationCode authorizationCode = authorizationCodeMapper.selectOne(queryWrapper);
        if (authorizationCode == null) {
            responseBody.put("error_message", "授权码错误");
            return ResponseEntity.badRequest().body(responseBody);
        }

        Map<String, Object> accessClaims = new HashMap<>();
        accessClaims.put("id", authorizationCode.getCodeUserId());
        accessClaims.put("ifNickname", authorizationCode.isCodeIfNickName());
        accessClaims.put("ifEmail", authorizationCode.isCodeIfEmail());
        accessClaims.put("ifAvatar", authorizationCode.isCodeIfAvatar());
        accessClaims.put("ifIntroduction", authorizationCode.isCodeIfIntroduction());
        // 有效期5分钟
        String accessToken = JwtTool.createJwtWithClaims(Integer.toString(authorizationCode.getCodeUserId()), accessClaims, 5 * 60 * 1000);

        //有效期十四天，但是不用用户id了
        String refreshToken = JwtTool.createJwtWithClaims(authorizationCode.getCodeWord(), accessClaims, 1000 * 60 * 60 * 24 * 14);

        responseBody.put("accessToken", accessToken);
        responseBody.put("refreshToken", refreshToken);
        responseBody.put("state", state);

        authorizationCodeMapper.delete(queryWrapper);

        return ResponseEntity.ok(responseBody);
    }
}
